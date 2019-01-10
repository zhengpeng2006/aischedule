package com.asiainfo.aif.schedule;

import com.ai.appframe2.common.ServiceManager;
import com.asiainfo.schedule.core.client.TaskDealParam;
import com.asiainfo.schedule.core.client.tf.AbstractTfTaskDeal;
import com.asiainfo.schedule.core.client.tf.config.table.Column;
import com.asiainfo.schedule.core.client.tf.util.QueryUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.*;

@SuppressWarnings("rawtypes")
public class DemoTfTask extends AbstractTfTaskDeal {
    private static final Logger logger = LoggerFactory.getLogger(com.asiainfo.schedule.test.DemoTfTask.class);

    @SuppressWarnings("unchecked")
    @Override
    protected void transform(HashMap[] tasks)
            throws Exception {

        logger.info("test tf running ....");
        TaskDealParam taskDealParam = this.getTaskDealParam();
        Map<String, String> taskParam = taskDealParam.getTaskParam();
        Connection conn = null;
        try {
            // TODO 获取或者写死源表名
            String srcTableName = "demo_tf_a";
            // TODO 获取或者写死源数据源
            String srcDbAcctCode = "base";
            Column[] srcColumns = getColumn(srcTableName, srcDbAcctCode);
            // TODO 获取或者写死目标表名
            String destTableName = "demo_tf_b";
            // TODO 获取或者写死源数据源
            String destDbAcctCode = "base";
            Column[] destColumns = getColumn(destTableName, destDbAcctCode);

            // 暂时写死用于测试，后边需要读表匹配映射
            Map columnsMapping = new HashMap();
            columnsMapping.put("TEST01", "TEST1");
            columnsMapping.put("TEST02", "TEST2");
            columnsMapping.put("TEST03", "TEST3");

            // 配置字段映射,并保证字段顺序与原来一致 便于取值
            Column[] mappedColumns = makeColMapping(srcColumns, columnsMapping);

            String finishSQL = this.createDestFinishSQL(destTableName, mappedColumns);
            conn = ServiceManager.getSession().getConnection(destDbAcctCode);
            PreparedStatement preparedStatement = conn.prepareStatement(finishSQL);
            for (HashMap data : tasks) {
                for (int i = 0; i < mappedColumns.length; i++) {
                    setStatement(preparedStatement,
                            mappedColumns[i].getType(),
                            data.get(mappedColumns[i].getSrcCloumnName()),
                            i + 1);
                }
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (Exception e) {
            logger.error("");
        }
    }

    /**
     * 根据表名和数据源获取表结构
     *
     * @param tableName
     * @param dbAcctCode
     * @return
     */
    public Column[] getColumn(String tableName, String dbAcctCode)
            throws Exception {
        return QueryUtil.readTableColumns(dbAcctCode, tableName);
    }

    /**
     * 设置字段映射
     *
     * @param srcColumns
     * @param
     * @return
     */
    public Column[] makeColMapping(Column[] srcColumns, Map columnsMapping) {
        Column[] mappedColumns = new Column[srcColumns.length];
        for (int i = 0; i < srcColumns.length; i++) {
            Column mappedColumn = new Column();
            String srcColumnName = srcColumns[i].getName();
            if (!StringUtils.isEmpty(srcColumnName)) {
                // 设置原字段名
                mappedColumn.setSrcCloumnName(srcColumnName);
                if (columnsMapping.containsKey(srcColumnName)) {// 源表和目标表不一致的字段
                    // TODO 转换字段名
                    String destColName = String.valueOf(columnsMapping.get(srcColumnName));
                    // 设置目标字段名
                    mappedColumn.setName(destColName);
                    // 设置字段类型
                    mappedColumn.setType(srcColumns[i].getType());

                    mappedColumns[i] = mappedColumn;
                } else {
                    // 设置目标字段名
                    mappedColumn.setName(srcColumnName);
                    // 设置字段类型
                    mappedColumn.setType(srcColumns[i].getType());

                    mappedColumns[i] = mappedColumn;
                }
            }
        }
        return mappedColumns;
    }

    /**
     * 创建目的表的完成sql
     *
     * @param tableName
     * @param columns
     * @return
     */
    public String createDestFinishSQL(String tableName, Column[] columns) {
        List<String> names = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        for (int i = 0; i < columns.length; i++) {
            names.add(columns[i].getName());
            values.add("?");
        }
        String strName = StringUtils.join(names.iterator(), ",");
        String strValue = StringUtils.join(values.iterator(), ",");
        String sql = "insert into " + tableName + " (" + strName + ") values(" + strValue + ")";
        return sql;
    }

    /**
     * 根据column中的type给statement赋值
     *
     * @param preparedStatement
     * @param type
     * @param dataVale
     * @param index
     * @throws Exception
     */
    public void setStatement(PreparedStatement preparedStatement, Class type, Object dataVale, int index)
            throws Exception {
        if (type == String.class)
            preparedStatement.setString(index, (String) dataVale);
        if (type == Long.class || type == Double.class || type == Float.class || type == long.class
                || type == double.class || type == float.class)
            preparedStatement.setLong(index, Long.parseLong(dataVale.toString()));
        if (type == Timestamp.class)
            preparedStatement.setTimestamp(index, new Timestamp(((Date) dataVale).getTime()));
    }
}
