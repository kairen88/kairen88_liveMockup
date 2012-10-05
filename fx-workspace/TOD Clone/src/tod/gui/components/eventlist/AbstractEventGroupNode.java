/*
TOD - Trace Oriented Debugger.
Copyright (c) 2006-2008, Guillaume Pothier
All rights reserved.

This program is free software; you can redistribute it and/or 
modify it under the terms of the GNU General Public License 
version 2 as published by the Free Software Foundation.

This program is distributed in the hope that it will be useful, 
but WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
General Public License for more details.

You should have received a copy of the GNU General Public License 
along with this program; if not, write to the Free Software 
Foundation, Inc., 59 Temple Place, Suite 330, Boston, 
MA 02111-1307 USA

Parts of this work rely on the MD5 algorithm "derived from the 
RSA Data Security, Inc. MD5 Message-Digest Algorithm".
*/
package tod.gui.components.eventlist;

import tod.core.database.browser.GroupingEventBrowser.EventGroup;
import tod.gui.IGUIManager;

/**
 * Base class for nodes that represent groups of events
 * @author gpothier
 */
public abstract class AbstractEventGroupNode<K> extends AbstractEventNode
{
	private EventGroup<K> itsGroup;

	public AbstractEventGroupNode(IGUIManager aGUIManager, EventListPanel aListPanel, EventGroup<K> aGroup)
	{
		super(aGUIManager, aListPanel);
		itsGroup = aGroup;
	}
	
	public EventGroup<K> getGroup()
	{
		return itsGroup;
	}
	
	public K getGroupKey() 
	{
		return getGroup().getGroupKey();
	}
	
	@Override
	protected EventGroup<K> getEvent()
	{
		return itsGroup;
	}
	
	public abstract Iterable<AbstractEventNode> getChildrenNodes();
}
