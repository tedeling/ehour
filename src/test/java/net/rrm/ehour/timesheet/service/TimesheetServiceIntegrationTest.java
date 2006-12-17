/**
 * Created on Dec 17, 2006
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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import net.rrm.ehour.dao.BaseDAOTest;
import net.rrm.ehour.exception.ObjectNotFoundException;
import net.rrm.ehour.timesheet.dto.BookedDay;

/**
 * TODO 
 **/

public class TimesheetServiceIntegrationTest extends BaseDAOTest
{
	private TimesheetService	timesheetService;
	
	

	/**
	 * Test method for {@link net.rrm.ehour.timesheet.service.TimesheetServiceImpl#getBookedDaysMonthOverview(java.lang.Integer, java.util.Calendar)}.
	 */
	public void testGetBookedDaysMonthOverview() throws ObjectNotFoundException
	{
		Calendar	cal = new GregorianCalendar(2006, 10 - 1, 1);
		List<BookedDay> res = timesheetService.getBookedDaysMonthOverview(1, cal);
		
		assertEquals(8.0, res.get(0).getHours());
		assertEquals(9.0, res.get(2).getHours());
	}



	/**
	 * @param timesheetService the timesheetService to set
	 */
	public void setTimesheetService(TimesheetService timesheetService)
	{
		this.timesheetService = timesheetService;
	}

	
	protected String[] getConfigLocations()
	{
		return new String[] { "classpath:/applicationContext-datasource.xml",
							  "classpath:/applicationContext-dao.xml", 
							  "classpath:/applicationContext-service.xml"};	
	}
}
