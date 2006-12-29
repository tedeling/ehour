/**
 * Created on Dec 28, 2006
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

package net.rrm.ehour.timesheet.dao;

import java.util.List;

import net.rrm.ehour.dao.GenericDAOHibernateImpl;
import net.rrm.ehour.data.DateRange;
import net.rrm.ehour.timesheet.domain.TimesheetComment;
import net.rrm.ehour.timesheet.domain.TimesheetCommentId;

/**
 * CRUD on timesheetComment domain obj 
 **/

public class TimesheetCommentDAOHibernateImpl 
			extends GenericDAOHibernateImpl<TimesheetComment, TimesheetCommentId>
			implements TimesheetCommentDAO
{
	/**
	 * @todo fix this a bit better
	 */
	public TimesheetCommentDAOHibernateImpl()
	{
		super(TimesheetComment.class);
	}	
	
	/* (non-Javadoc)
	 * @see net.rrm.ehour.timesheet.dao.TimesheetCommentDAO#findForUserInRage(java.lang.Integer, net.rrm.ehour.data.DateRange)
	 */
	@SuppressWarnings("unchecked")
	public List<TimesheetComment> findForUserInRage(Integer userId, DateRange dateRange)
	{
		List<TimesheetComment> comments;
		String[]	keys = new String[3];
		Object[]	params = new Object[3];
		
		keys[0] = "dateStart";
		keys[1] = "dateEnd";
		keys[2] = "userId";
		
		params[0] = dateRange.getDateStart();
		params[1] = dateRange.getDateEnd();
		params[2] = userId;
		
		comments = getHibernateTemplate().findByNamedQueryAndNamedParam("TimesheetComment.getCommentsForUserIdInRange"
																		, keys, params);
		
		return comments;			
	}
}
