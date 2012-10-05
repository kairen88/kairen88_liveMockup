/*
 * Created on Apr 12, 2005
 */
package zz.snipsnap.plugin.media.rewriter;

/**
 * Implementations of this interface specify query transformations.
 * @author gpothier
 */
public interface IQueryTransformer
{
	/**
	 * Transforms the given query. The returned object can be the same as the one passed to
	 * the method, possibly modified, or it can be another one.
	 */
	public MediaQueryRewriter.MediaQuery transform (MediaQueryRewriter.MediaQuery aQuery);

}
