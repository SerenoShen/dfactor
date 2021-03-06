package fun.lib.actor.example;

import fun.lib.actor.api.cb.CbTimeout;
import fun.lib.actor.core.DFActor;
import fun.lib.actor.core.DFActorManager;
/**
 * 定时器使用示例
 * @author lostsky
 *
 */
public final class Timeout {

	public static void main(String[] args) {
		final DFActorManager mgr = DFActorManager.get();
		//启动入口actor，开始消息循环		
		mgr.start(EntryActor.class);
	}

	/**
	 * 入口actor
	 * @author lostsky
	 *
	 */
	private static class EntryActor extends DFActor{
		public EntryActor(Integer id, String name, Boolean isBlockActor) {
			super(id, name, isBlockActor);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onStart(Object param) {
			//使用自带日志打印
			log.info("EntryActor start, curThread="+Thread.currentThread().getName());
			//启动定时器
			timer.timeout(1000, 1);
		}
		
		private int timeoutCount = 0;  //计数器
		@Override
		public void onTimeout(int requestId) {
			log.info("onTimeout(Instance call), count="+(++timeoutCount)+", requestId="+requestId+", curThread="+Thread.currentThread().getName());
			timer.timeout(1000, new CbTimeout() {
				@Override
				public void onTimeout() {
					log.info("onTimeout(CbFunc call), count="+(++timeoutCount)+", curThread="+Thread.currentThread().getName());
					timer.timeout(1000, 1);   
				}
			});
		}		
		
	}
}
