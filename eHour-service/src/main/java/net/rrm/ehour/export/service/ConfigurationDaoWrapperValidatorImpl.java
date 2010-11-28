package net.rrm.ehour.export.service;

import net.rrm.ehour.domain.Configuration;

/**
 * @author thies (Thies Edeling - thies@te-con.nl)
 *         Created on: 11/28/10 - 1:27 AM
 */
public class ConfigurationDaoWrapperValidatorImpl implements ConfigurationDaoWrapper
{
    private int count;

    @Override
    public void persist(Configuration config)
    {
        count++;
    }

    public int getCount()
    {
        return count;
    }
}
