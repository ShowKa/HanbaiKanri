package com.showka.system;

import org.jooq.ExecuteContext;
import org.jooq.impl.DefaultExecuteListener;

/**
 * Jooq event listener.
 * 
 * <pre>
 * start の他、いろいろなタイミングで処理を挟むことができる。
 * 有効化するには次のようにDSLContextに設定する必要がある。
 * 
 * &#64;Autowired
 * protected DSLContext create;
 * create.configuration().set(new DefaultExecuteListenerProvider(new ExecuteListenerExtend()));
 * </pre>
 *
 */
public class ExecuteListenerExtend extends DefaultExecuteListener {

	/**
	 * SID.
	 */
	private static final long serialVersionUID = -751314316504739579L;

	@Override
	public void start(ExecuteContext ctx) {
		System.out.println("------ sql -------");
		System.out.println(ctx.query());
		System.out.println(ctx.query().getParams());
	}

	@Override
	public void fetchEnd(ExecuteContext ctx) {
		if (ctx.result() != null) {
			System.out.println("size: " + ctx.result().size());
		} else {
			System.out.println("size: 0");
		}
		System.out.println("------------------");
	}
}
