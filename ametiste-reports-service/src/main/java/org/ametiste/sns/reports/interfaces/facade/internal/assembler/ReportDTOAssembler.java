package org.ametiste.sns.reports.interfaces.facade.internal.assembler;

import org.ametiste.ifaces.facade.dto.assembler.DTOAssembler;
import org.ametiste.sns.reports.interfaces.facade.dto.ReportViewDTO;
import org.ametiste.sns.reports.model.Report;

import java.util.ArrayList;
import java.util.List;

public class ReportDTOAssembler implements DTOAssembler<Report, ReportViewDTO> {

	@Override
	public ReportViewDTO assembleDTO(Report report) {

		if (report == null) {
			throw new IllegalArgumentException("Can't assemble dto from the null object.");
		}

		ReportViewDTO reportDTO = new ReportViewDTO();
		reportDTO.setDate(report.getDate());
		reportDTO.setId(report.getId());
		reportDTO.setSender(report.getSender());
		reportDTO.setType(report.getType());
		reportDTO.setContent(report.getContent());

		return reportDTO;
	}

	@Override
	public List<ReportViewDTO> assembleDTOList(List<Report> objectsList) {

		List<ReportViewDTO> reportsList = new ArrayList<ReportViewDTO>(objectsList.size());

		for (Report report : objectsList) {
			reportsList.add(assembleDTO(report));
		}

		return reportsList;
	}

}
