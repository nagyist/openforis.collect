package org.openforis.collect.io.metadata.collectearth.balloon;

import org.apache.commons.lang3.StringUtils;


/**
 * 
 * @author A. Sanchez-Paus Diaz
 * @author S. Ricci
 *
 */
class CEComponent {

	private String htmlParameterName;
	private String name;
	private String label;
	private boolean multiple;
	boolean hideWhenNotRelevant = false;

	public CEComponent(String htmlParameterName, String name, String label, boolean multiple) {
		super();
		this.htmlParameterName = htmlParameterName;
		this.name = name;
		this.label = label;
		this.multiple = multiple;
	}

	public String getLabelOrName() {
		return  HtmlUnicodeEscaperUtil.escapeHtmlUnicode( StringUtils.isBlank(label) ? name: label );
	}

	public String getHtmlParameterName() {
		return htmlParameterName;
	}
	
	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public boolean isMultiple() {
		return multiple;
	}
}