package com.asiainfo.batchwriteoff.table;

/**
 * <p>
 * 
 * @Description
 * </p>
 * <p>
 * @company Asiainfo-linkage (NJ)
 *          </p>
 *          <p>
 * @author 闫瑞国
 *         </p>
 *         <p>
 * @Email yanrg@asiainfo-linkage.com
 *        </p>
 *        <p>
 * @createDate Oct 11, 2012 4:31:00 PM
 *             </p>
 *             <p>
 * @modifyDate
 *             </p>
 *             <p>
 * @version 1.0
 *          </p>
 */
public class Index {

	private String idxName;
	private Column[] cols;

	public String getIdxName() {
		return idxName;
	}

	public void setIdxName(String idxName) {
		this.idxName = idxName;
	}

	public Column[] getCols() {
		return cols;
	}

	public void setCols(Column[] cols) {
		this.cols = cols;
	}
}
