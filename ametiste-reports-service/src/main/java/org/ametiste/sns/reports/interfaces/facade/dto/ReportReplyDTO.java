package org.ametiste.sns.reports.interfaces.facade.dto;

import java.util.List;

public class ReportReplyDTO {

	private boolean is_truncated;
	private long offset;
	private int size;
	private List<ReportViewDTO> reports;

	public boolean isIs_truncated() {
		return is_truncated;
	}

	public void setIsTruncated(boolean truncated) {
		this.is_truncated = truncated;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<ReportViewDTO> getReports() {
		return reports;
	}

	public void setReports(List<ReportViewDTO> reports) {
		this.reports = reports;
	}

}
