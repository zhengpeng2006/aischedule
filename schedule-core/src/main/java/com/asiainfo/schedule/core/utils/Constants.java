package com.asiainfo.schedule.core.utils;

public class Constants {
	public static enum RunSts {
		start, stop
	}
	
	public static enum JobSts{
		C,R,F
	}

	public static enum ServerType {
		manager, processor
	}

	public static enum TaskType {
		single, complex, reload, tf;
		public boolean isComplex() {
			return this.equals(complex) || this.equals(tf);
		}
	}

	public static enum ScheduleType {
		start, end
	}

	public static enum TaskAssignType {
		Strategy, Manual
	}

	public static enum KeyCodes {
		TaskCode,TaskType,TaskName,TaskVersion,TaskGroup,TaskItemId, ServerId, TaskRunSts, TaskJobId, CreateTime, Creator
	}

	/**
	 * 告警级别。重要级别依次递增
	 */
	public interface TaskErrLevel {

		// 主进程正常, 备用进程挂了, 任务正常执行
		String LEVEL_1 = "1",

		// 主进程挂了 任务切换到备进程 任务正常执行
		 LEVEL_2 = "2",

		// 主进程正常 但是任务执行失败
		 LEVEL_3 = "3",

		// 主进程挂掉 任务切换到备进程 任务执行失败
		 LEVEL_4 = "4",

		// 主备进程均挂掉 任务未执行
		 LEVEL_5 = "5",

		// manager处理流水号异常
		 LEVEL_6 = "6",

		// processor刷新异常.任务无法处理
		 LEVEL_7 = "7",

		// 任务进入等待队列，堵塞
		 LEVEL_8 = "8",

		// monitor异常
		 LEVEL_9 = "9",

		// 界面调度任务异常
		 LEVEL_10 = "10";

	}

	public static final String ONLY_SPLIT_REGION_FLAG = "@@REGION";
	public static final String ABG_MON_BUSI_LOG = "ABG_MON_BUSI_LOG";
}
