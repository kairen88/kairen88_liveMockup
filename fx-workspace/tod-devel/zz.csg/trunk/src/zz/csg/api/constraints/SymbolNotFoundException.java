package zz.csg.api.constraints;

/**
 * This exception is thrown when an expression references a symbol that was not previously
 * declared.
 * @author gpothier
 */
public class SymbolNotFoundException extends Exception
{
	public SymbolNotFoundException(String aSymbolName)
	{
		super("Symbol not found: '"+aSymbolName+"'");
	}
}