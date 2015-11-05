/**
 * 
 */
package org.openforis.collect.designer.form;

import org.openforis.collect.metamodel.CollectAnnotations.Annotation;
import org.openforis.collect.metamodel.ui.UIOptions;
import org.openforis.collect.metamodel.ui.UIOptions.CodeAttributeLayoutType;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.CodeList;

/**
 * @author S. Ricci
 *
 */
public class CodeAttributeDefinitionFormObject extends AttributeDefinitionFormObject<CodeAttributeDefinition> {
	
	private Integer codeListId;
	private Integer parentCodeAttributeDefinitionId;
	private boolean strict;
	private boolean allowValuesSorting;
	private String layoutType;
	private boolean showAllowedValuesPreview;
	private String layoutDirection;
	private boolean showCode;
	private String hierarchicalLevel;
	
	CodeAttributeDefinitionFormObject() {
		super();
		strict = true;
		allowValuesSorting = false;
		showAllowedValuesPreview = (Boolean) Annotation.SHOW_ALLOWED_VALUES_PREVIEW.getDefaultValue();
		layoutType = ((CodeAttributeLayoutType) Annotation.CODE_ATTRIBUTE_LAYOUT_TYPE.getDefaultValue()).name();
		layoutDirection = Annotation.CODE_ATTRIBUTE_LAYOUT_DIRECTION.getDefaultValue();
		showCode = (Boolean) Annotation.CODE_ATTRIBUTE_SHOW_CODE.getDefaultValue();
	}

	@Override
	public void saveTo(CodeAttributeDefinition dest, String languageCode) {
		super.saveTo(dest, languageCode);
		CodeList codeList = codeListId == null ? null : dest.getSurvey().getCodeListById(codeListId);
		dest.setList(codeList);
		dest.setAllowUnlisted(! strict);
		CodeAttributeDefinition parentCodeAttributeDef = parentCodeAttributeDefinitionId == null ? null : 
			(CodeAttributeDefinition) dest.getSurvey().getSchema().getDefinitionById(parentCodeAttributeDefinitionId);
		dest.setParentCodeAttributeDefinition(parentCodeAttributeDef);
		dest.setAllowValuesSorting(dest.isMultiple() && allowValuesSorting);
		
		CollectSurvey survey = (CollectSurvey) dest.getSurvey();
		UIOptions uiOptions = survey.getUIOptions();
		uiOptions.setShowAllowedValuesPreviewValue(dest, showAllowedValuesPreview);
		
		uiOptions.setLayoutType(dest, CodeAttributeLayoutType.valueOf(layoutType.toUpperCase()));
		uiOptions.setLayoutDirection(dest, layoutDirection);
		uiOptions.setShowCode(dest, showCode);
	}
	
	@Override
	public void loadFrom(CodeAttributeDefinition source, String languageCode) {
		super.loadFrom(source, languageCode);
		codeListId = source.getList() == null ? null : source.getList().getId();
		parentCodeAttributeDefinitionId = source.getParentCodeAttributeDefinition() == null ? null :source.getParentCodeAttributeDefinition().getId();
		hierarchicalLevel = codeListId == null ? null : source.getHierarchicalLevel();
		strict = ! source.isAllowUnlisted();
		allowValuesSorting = source.isMultiple() && source.isAllowValuesSorting();
		
		CollectSurvey survey = (CollectSurvey) source.getSurvey();
		UIOptions uiOptions = survey.getUIOptions();
		showAllowedValuesPreview = uiOptions.getShowAllowedValuesPreviewValue(source);
		
		layoutType = uiOptions.getLayoutType(source).toString();
		layoutDirection = uiOptions.getLayoutDirection(source);
		showCode = uiOptions.getShowCode(source);
	}
	
	public Integer getCodeListId() {
		return codeListId;
	}
	
	public void setCodeListId(Integer codeListId) {
		this.codeListId = codeListId;
	}

	public boolean isStrict() {
		return strict;
	}

	public void setStrict(boolean strict) {
		this.strict = strict;
	}
	
	public boolean isAllowValuesSorting() {
		return allowValuesSorting;
	}
	
	public void setAllowValuesSorting(boolean allowValuesSorting) {
		this.allowValuesSorting = allowValuesSorting;
	}
	
	public Integer getParentCodeAttributeDefinitionId() {
		return parentCodeAttributeDefinitionId;
	}
	
	public void setParentCodeAttributeDefinitionId(Integer parentCodeAttributeDefinitionId) {
		this.parentCodeAttributeDefinitionId = parentCodeAttributeDefinitionId;
	}
	
	public String getHierarchicalLevel() {
		return hierarchicalLevel;
	}
	
	public void setHierarchicalLevel(String hierarchicalLevel) {
		this.hierarchicalLevel = hierarchicalLevel;
	}

	public String getLayoutDirection() {
		return layoutDirection;
	}
	
	public void setLayoutDirection(String layoutDirection) {
		this.layoutDirection = layoutDirection;
	}
	
	public String getLayoutType() {
		return layoutType;
	}
	
	public void setLayoutType(String layoutType) {
		this.layoutType = layoutType;
	}
	
	public boolean isShowAllowedValuesPreview() {
		return showAllowedValuesPreview;
	}

	public void setShowAllowedValuesPreview(boolean showAllowedValuesPreview) {
		this.showAllowedValuesPreview = showAllowedValuesPreview;
	}

	public boolean isShowCode() {
		return showCode;
	}

	public void setShowCode(boolean showCode) {
		this.showCode = showCode;
	}
	
}
