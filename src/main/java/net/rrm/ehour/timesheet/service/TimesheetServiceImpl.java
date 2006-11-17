/*
 * Created on Nov 4, 2006
 * Created by Thies Edeling
 * Copyright (C) 2005, 2006 te-con, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 * thies@te-con.nl
 * TE-CON
 * Legmeerstraat 4-2h, 1058ND, AMSTERDAM, The Netherlands
 *
 */

package net.rrm.ehour.timesheet.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.rrm.ehour.data.DateRange;
import net.rrm.ehour.exception.ObjectNotFoundException;
import net.rrm.ehour.report.service.ReportService;
import net.rrm.ehour.timesheet.dao.TimesheetDAO;
import net.rrm.ehour.timesheet.domain.TimesheetEntry;
import net.rrm.ehour.timesheet.dto.BookedDay;
import net.rrm.ehour.timesheet.dto.BookedDayComparator;
import net.rrm.ehour.timesheet.dto.TimesheetOverview;
import net.rrm.ehour.util.DateUtil;
import net.rrm.ehour.util.EhourConfig;

/**
 * Provides services for displaying and manipulating timesheets.
 * Methods are organized by their functionality rather than technical impact.
 * 
 * @author Thies
 *
 */

public class TimesheetServiceImpl implements TimesheetService
{
	private	TimesheetDAO	timesheetDAO;
	private	ReportService	reportService;
	private	EhourConfig		configuration;
	
	/**
	 * Fetch the timesheet overview for a user. This returns an object containing the project assignments for the
	 * requested month and a list with all timesheet entries for that month.
	 * @param userId
	 * @param requestedMonth only the month and year of the calendar is used
	 * @return TimesheetOverviewAction
	 * @throws ObjectNotFoundException
	 */	

	public TimesheetOverview getTimesheetOverview(Integer userId, Calendar requestedMonth) throws ObjectNotFoundException
	{
		TimesheetOverview	overview = new TimesheetOverview();
		DateRange			monthRange;
		List				assignHours;
		List				timesheetEntries;
		Map					calendarMap;
		
		monthRange = DateUtil.calendarToMonthRange(requestedMonth);
		
		assignHours = reportService.getHoursPerAssignmentInRange(userId, monthRange);

		timesheetEntries = timesheetDAO.getTimesheetEntriesInRange(userId, monthRange);
		calendarMap = entriesToCalendarMap(timesheetEntries);
		
		overview.setProjectHours(assignHours);
		overview.setTimesheetEntries(calendarMap);
		
		return overview;
	}
	
	/**
	 * Get a list with all day numbers in this month that has complete booked days (config defines the completion
	 * level). 
	 * @param userId
	 * @param requestedMonth
	 * @return List with Integers of complete booked days
	 * @throws ObjectNotFoundException
	 */
	public List getBookedDaysMonthOverview(Integer userId, Calendar requestedMonth) throws ObjectNotFoundException
	{
		DateRange	monthRange;
		List		bookedDays;
		List		bookedDaysReturn = new ArrayList();
		Iterator	iterator;
		BookedDay	bookedDay;
		
		monthRange = DateUtil.calendarToMonthRange(requestedMonth);
		
		bookedDays = timesheetDAO.getBookedHoursperDayInRange(userId, monthRange);
		
		iterator = bookedDays.iterator();
		
		while (iterator.hasNext())
		{
			bookedDay = (BookedDay)iterator.next();
			
			if (bookedDay.getHours().doubleValue() >= configuration.getCompleteDayHours())
			{
				bookedDaysReturn.add(bookedDay);
			}
		}
		
		Collections.sort(bookedDays, new BookedDayComparator());
		
		return bookedDays;
	}		
	
	/**
	 * Put the timesheet entries in a map where the day is the key and
	 * the value is a list of timesheet entries filled out for that date
	 * @param timesheetEntries
	 * @return
	 */
	private Map entriesToCalendarMap(List timesheetEntries)
	{
		Map				calendarMap;
		Iterator		i;
		TimesheetEntry	entry;
		Calendar		cal;
		Integer			dayKey;
		List			dayEntries;
		
		i = timesheetEntries.iterator();
		calendarMap = new HashMap();
		
        i = timesheetEntries.iterator();

        while (i.hasNext())
        {
           entry = (TimesheetEntry)i.next();
           
           cal = new GregorianCalendar();
           cal.setTime(entry.getEntryId().getEntryDate());
           
           dayKey = new Integer(cal.get(Calendar.DAY_OF_MONTH));

           if (calendarMap.containsKey(dayKey))
           {
               dayEntries = (List)calendarMap.get(dayKey);
           }
           else
           {
               dayEntries = new ArrayList();
           }

           dayEntries.add(entry);

           calendarMap.put(dayKey, dayEntries);
        }		
		
		return calendarMap;
	}

	/**
	 * DAO setter (Spring)
	 * @param dao
	 */
	public void setTimesheetDAO(TimesheetDAO dao)
	{
		timesheetDAO = dao;
	}

	/**
	 * Report setter (Spring)
	 * @param dao
	 */	
	
	public void setReportService(ReportService reportService)
	{
		this.reportService = reportService;
	}
	
	/**
	 * Setter for the config
	 * @param config
	 */
	public void setEhourConfiguration(EhourConfig config)
	{
		this.configuration = config;
	}
}
