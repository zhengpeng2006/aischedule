# x86调度管理平台

x86调度管理平台(下文称为AiSchedule)是一套分布式的后台任务调度管理平台。

### 特点
* 具备水平扩展能力，可能进行性能压力分摊，提升并行处理能力。

* 进程、任务统一由调度平台管理，实现统一管控。

* 统一部署以及统一主机、进程、业务监控。

## 搭建AiSchedule
搭建AiSchedule的前提条件：
* linux环境
* Maven(建议使用 3.2.5以上)
* Java 6 至 Java 8

## 任务接入AiSchedule
AiSchedule为接入方提供了以下接口：
* 简单类型任务接口 com.asiainfo.schedule.core.client.interfaces.ISingleTaskDeal
特点：单线程执行，处理中的任务不可终止。
缺点：执行过程不可监控，只能查看执行结果是否成功。
适用场景：间隔时间长、逻辑简单、处理时间短。如每天的某个时间刷新缓存。

* 复杂类型任务接口 com.asiainfo.schedule.core.client.interfaces.IComplexTaskDeal
特点：多线程执行，处理中的任务可随时终止。执行过程可监控，包括任务处理的数据量、耗时、失败数等。
缺点：实现的方法相对较多。
适用场景：定时、间隔大数据量处理，实时工单处理等。

### 简单任务示例
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.schedule.core.client.TaskDealParam;
import com.asiainfo.schedule.core.client.interfaces.ISingleTaskDeal;

public class DemoSingleTask implements ISingleTaskDeal {
    private static final transient Logger logger = LoggerFactory.getLogger(DemoSingleTask.class);

    private TaskDealParam param;

    @Override
    public void init(TaskDealParam param) throws Exception {
        this.param = param;
    }

    @Override
    public void execute() throws Exception {
        logger.info("Hello World!" + param);
    }
}
```
### 复杂任务示例
```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.schedule.core.client.TaskDealParam;
import com.asiainfo.schedule.core.client.interfaces.IComplexTaskDeal;

public class DemoComplexTask2 implements IComplexTaskDeal<List<Long>> {
    private static Log log = LogFactory.getLog(DemoComplexTask.class);

    private TaskDealParam param;
    int total = 359;
    LinkedList<List<Long>> allDatas = new LinkedList<List<Long>>();

    public Comparator<List<Long>> getComparator() {
        return new Comparator<List<Long>>() {
            public int compare(List<Long> o1, List<Long> o2) {
                return -1;
            }
        };
    }

    @Override
    public void init(TaskDealParam param) throws Exception {
        this.param = param;
        Random r = new Random();
        int j = 2;
        for (int i = 0; i < 200; i++) {
            j = 2;
            ArrayList list = new ArrayList();
            while (true) {
                if (j <= 0) {
                    break;
                }
                list.add(r.nextLong());
                j--;
            }
            if (i == 0) {
                list.add(1L);
            }
            allDatas.add(list);
        }
        log.info("gen data over!!!" + allDatas.size());
        Thread.currentThread().sleep(5000);
    }

    @Override
    public boolean isFinishNoData() {
        return true;
    }

    @Override
    public long getTaskDataTotal() throws Exception {
        return total;
    }

    @Override
    public List<List<Long>> selectTasks() throws Exception {
        List<List<Long>> selects = new ArrayList<List<Long>>();
        if (allDatas != null && allDatas.size() > 0) {
            int size = this.param.getEachFetchDataNum();
            while (size > 0 && allDatas.size() > 0) {
                selects.add(allDatas.removeFirst());
                size--;
            }
        }
        return selects;
    }

    @Override
    public void execute(List<List<Long>> tasks) throws Exception {
        for (List<Long> t : tasks) {
            if (t.size() == 3) {
                log.info("sleep!!!!!!!!!!!!!!!!!!!!!!");
                Thread.currentThread().sleep(30000);
            }
        }
        log.info(tasks);
    }
}
```
## 文档
部署手册、操作手册等文档位于dcos源码目录中。


