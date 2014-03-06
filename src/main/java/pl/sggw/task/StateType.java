package pl.sggw.task;

/**
 * @author Daniel
 * @date 30.10.12
 */

public enum StateType {
	IN_QUEUE {
		@Override
		public String getServerStatus() {
			return "needsAction";
		}
	},
	DONE {
		@Override
		public String getServerStatus() {
			return "completed";
		}
	},
	REMOVED {
		@Override
		public String getServerStatus() {
			return null;
		}
	};

	public abstract String getServerStatus();
}
