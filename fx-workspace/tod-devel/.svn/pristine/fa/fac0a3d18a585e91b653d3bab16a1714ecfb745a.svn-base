/*
 * Created on Apr 5, 2005
 */
package zz.waltz.api.action.enable;

/**
 * This enumeration permits to specify a comparison type.
 * @author gpothier
 */
public enum Comparison
{
	/**
	 * Greater than
	 */
	GT { boolean accept(int aValue) { return aValue > 0; } },
	
	/**
	 * Lesser than
	 */
	LT { boolean accept(int aValue) { return aValue < 0; } },
	
	/**
	 * Greater than or equal
	 */
	GTE { boolean accept(int aValue) { return aValue >= 0; } },
	
	/**
	 * Lesser than or equal
	 */
	LTE { boolean accept(int aValue) { return aValue <= 0; } };
	
	/**
	 * Indicates if the comparison represented by this particular comparison type
	 * is verified for the specified comparison value
	 * @param aValue A comparsion value such as the one returned by {@link Comparable#compareTo(T)}
	 * or {@link java.util.Comparator#compare}
	 */
	abstract boolean accept (int aValue);
}