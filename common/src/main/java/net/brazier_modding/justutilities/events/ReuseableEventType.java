package net.brazier_modding.justutilities.events;

public class ReuseableEventType<T extends ReuseableEventType> {
	protected void resetObject(){}

	public static class Cancelable<T extends Cancelable> extends ReuseableEventType<T> {
		private boolean isCanceled = false;

		protected void cancel(){
			this.isCanceled = true;
		}

		public boolean isCanceled() {
			return isCanceled;
		}

		@Override
		protected void resetObject(){
			super.resetObject();
			this.isCanceled = false;
		}
	}
}
