/**
 * Created on Feb 5, 2007
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

package net.rrm.ehour.web.userreport.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rrm.ehour.report.project.ProjectReport;
import net.rrm.ehour.report.project.WeeklyProjectAssignmentAggregate;
import net.rrm.ehour.report.service.ReportService;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Week;

/**
 * TODO 
 **/

public class ProjectReportOverviewChartAction extends ProjectReportChartAction
{
	private	ReportService	reportService;
	
	/**
	 * Create chart
	 * @param report
	 * @return
	 */
	protected JFreeChart getChart(ProjectReport report)
	{
		List<WeeklyProjectAssignmentAggregate>	results;
		TimeSeries 		timeSeries;
		String[]		weekYear;
		Week			week;
		Map<Integer, TimeSeries>	seriesMap;
		TimeSeriesCollection	seriesCollection;
		
		seriesMap = new HashMap<Integer, TimeSeries>();
		
		
		results = reportService.createWeeklyProjectReport(report.getReportCriteria());

		for (WeeklyProjectAssignmentAggregate aggregate : results)
		{
			if (seriesMap.containsKey(aggregate.getCustomerId()))
			{
				timeSeries = seriesMap.get(aggregate.getCustomerId());
			}
			else
			{
				logger.debug("Adding series for " + aggregate.getCustomerName());
				timeSeries = new TimeSeries(aggregate.getCustomerName(), Week.class);
			}
			
			weekYear = aggregate.getWeekYear().split(" ");
			week = new Week(Integer.parseInt(weekYear[0]), Integer.parseInt(weekYear[1]));
			timeSeries.addOrUpdate(week, aggregate.getTotalHours());
			
			seriesMap.put(aggregate.getCustomerId(), timeSeries);
		}
		
		seriesCollection = new TimeSeriesCollection();
		
		for (Integer customerId: seriesMap.keySet())
		{
			seriesCollection.addSeries(seriesMap.get(customerId));
		}
		
		JFreeChart chart = ChartFactory.createTimeSeriesChart("Hours per customer", "Week", "Hours", 
				seriesCollection, true, true, true);
		
		return chart;
	}

	/**
	 * @param reportService the reportService to set
	 */
	public void setReportService(ReportService reportService)
	{
		this.reportService = reportService;
	}
}
