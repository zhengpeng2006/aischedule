package com.asiainfo.monitor.busi.exe.task.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import bsh.Interpreter;

import com.asiainfo.monitor.tools.common.interfaces.IKeyName;
import com.asiainfo.monitor.tools.common.interfaces.ITaskRtn;
import com.asiainfo.monitor.tools.util.TypeConst;

public abstract class InterpreterWrapper
{

    protected String id;

    protected String code;

    protected String name;

    protected IKeyName[] parameters;

    protected String expr;

    protected Interpreter interpreter;

    protected ITaskRtn taskRtn;

    public InterpreterWrapper()
    {
        interpreter = new Interpreter();
        interpreter.setClassLoader(Thread.currentThread().getContextClassLoader());
    }

    public InterpreterWrapper(String id, String code, String name, IKeyName[] parameters, String expr, ITaskRtn taskRtn)
    {
        interpreter = new Interpreter();
        interpreter.setClassLoader(Thread.currentThread().getContextClassLoader());
        this.id = id;
        this.code = code;
        this.name = name;
        this.parameters = parameters;
        this.expr = expr;
        this.taskRtn = taskRtn;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public IKeyName[] getParameters()
    {
        return parameters;
    }

    public void setParameters(IKeyName[] parameters)
    {
        this.parameters = parameters;
    }

    public String getExpr()
    {
        return expr;
    }

    public void setExpr(String expr)
    {
        this.expr = expr;
    }

    public Interpreter getInterpreter()
    {
        return interpreter;
    }

    public void setInterpreter(Interpreter interpreter)
    {
        this.interpreter = interpreter;
    }

    public ITaskRtn getTaskRtn()
    {
        return taskRtn;
    }

    public void setTaskRtn(ITaskRtn taskRtn)
    {
        this.taskRtn = taskRtn;
    }

    /**
     * 查找参数
     * @param param
     * @return
     */
    protected IKeyName findParameter(String param)
    {
        if(parameters == null)
            return null;
        IKeyName result = null;
        for(int i = 0; i < parameters.length; i++) {
            if(parameters[i].getName().equals(name)) {
                result = parameters[i];
                break;
            }
        }
        return result;
    }

    /**
     * 执行解析命令
     * @throws Exception
     */
    public void execute() throws Exception
    {

        if(parameters != null) {
            for(int i = 0; i < parameters.length; i++) {
                if(parameters[i].getName().startsWith("$"))
                    interpreter.set(parameters[i].getName(), parameters[i].getKey());
                else {
                    if(parameters[i].getName().equals(TypeConst.HOST + TypeConst._NAME)) {
                        interpreter.set("$HOST", parameters[i].getKey());
                    }
                    else
                        interpreter.set("$" + parameters[i].getName(), parameters[i].getKey());
                }
            }
        }

        interpreter.set("$TASKRTN", taskRtn);
        interpreter.set("$INFO_NAME", name);

        //分拆返回值
        String[] rtnValue = StringUtils.split(taskRtn.getMsg(), "|");
        // 设置参数
        if(rtnValue != null && rtnValue.length != 0) {
            Map map = new HashMap();
            for(int i = 0; i < rtnValue.length; i++) {
                String[] tmp = StringUtils.split(rtnValue[i], ":");
                if(map.containsKey(tmp[0])) {
                    List l = (List) map.get(tmp[0]);
                    l.add(tmp[1]);
                    map.put(tmp[0], l);
                }
                else {
                    List l = new ArrayList();
                    l.add(tmp[1]);
                    map.put(tmp[0], l);
                }
            }

            Set key = map.keySet();
            for(Iterator iter = key.iterator(); iter.hasNext();) {
                String item = (String) iter.next();
                String[] tmp = (String[]) ((List) map.get(item)).toArray(new String[0]);
                if(tmp != null) {
                    for(int i = 0; i < tmp.length; i++) {
                        if(tmp[i] != null) {
                            tmp[i] = tmp[i].trim();
                        }
                    }
                }
                interpreter.set("$" + item, tmp);
            }
        }
        interpreter();
        interpreter.eval(expr);
        interpreter = null;
    }

    public abstract void interpreter() throws Exception;
}
