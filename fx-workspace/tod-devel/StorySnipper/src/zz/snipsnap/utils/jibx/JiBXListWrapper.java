/*
 * Created on Apr 4, 2005
 */
package zz.snipsnap.utils.jibx;

import java.util.List;

public class JiBXListWrapper
{
    private List itsList;

	public JiBXListWrapper()
	{
	}

	public JiBXListWrapper(List aList)
	{
		itsList = aList;
	}

	public List getList()
	{
		return itsList;
	}
}
