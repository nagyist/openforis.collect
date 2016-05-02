/**
 * 
 */
package org.openforis.collect.designer.form;

import org.openforis.collect.metamodel.CollectAnnotations.Annotation;
import org.openforis.collect.metamodel.ui.UIOptions;
import org.openforis.collect.metamodel.ui.UIOptions.CodeAttributeLayoutType;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.commons.lang.Objects;
import org.openforis.idm.metamodel.CodeAttributeDefinition;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Survey;

/**
 * @author S. Ricci
 *
 */
public class CodeAttributeDefinitionFormObject extends AttributeDefinitionFormObject<CodeAttributeDefinition> {
	
	private Integer listId;
	private Integer parentCodeAttributeDefinitionId;
	private boolean strict;
	private boolean allowValuesSorting;
	private String layoutType;
	private boolean showAllowedValuesPreview;
	private String layoutDirection;
	private boolean showCode;
	private String hierarchicalLevel;
	
	public CodeAttributeDefinitionFormObject() {
	}
	
	public CodeAttributeDefinitionFormObject(EntityDefinition parentDefn) {
		super(parentDefn);
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
		dest.setList(listId == null ? null : dest.getSurvey().getCodeListById(listId));
		dest.setAllowUnlisted(! strict);
		dest.setParentCodeAttributeDefinition((CodeAttributeDefinition) getDefinition(dest.getSurvey(), parentCodeAttributeDefinitionId));
		dest.setAllowValuesSorting(dest.isMultiple() && allowValuesSorting);
		
		CollectSurvey survey = (CollectSurvey) dest.getSurvey();
		UIOptions uiOptions = survey.getUIOptions();
		uiOptions.setShowAllowedValuesPreviewValue(dest, showAllowedValuesPreview);
		
		uiOptions.setLayoutType(dest, CodeAttributeLayoutType.valueOf(layoutType.toUpperCase()));
		uiOptions.setLayoutDirection(dest, layoutDirection);
		uiOptions.setShowCode(dest, showCode);
	}

	private NodeDefinition getDefinition(Survey survey, Integer defId) {
		return defId == null ? null : survey.getSchema().getDefinitionById(defId);
	}
	
	@Override
	public void loadFrom(CodeAttributeDefinition source, String languageCode) {
		super.loadFrom(source, languageCode);
		listId = Objects.getPropertyValue(source.getList(), "id");
		parentCodeAttributeDefinitionId = Objects.getPropertyValue(source.getParentCodeAttributeDefinition(), "id");
		hierarchicalLevel = source.getList() == null ? null : source.getHierarchicalLevel();
		strict = ! source.isAllowUnlisted();
		allowValuesSorting = source.isMultiple() && source.isAllowValuesSorting();
		
		CollectSurvey survey = (CollectSurvey) source.getSurvey();
		UIOptions uiOptions = survey.getUIOptions();
		showAllowedValuesPreview = uiOptions.getShowAllowedValuesPreviewValue(source);
		
		layoutType = uiOptions.getLayoutType(source).toString();
		layoutDirection = uiOptions.getLayoutDirection(source);
		showCode = uiOptions.getShowCode(source);
	}
	
	public Integer getListId() {
		return listId;
	}
	
	public void setListId(Integer listId) {
		this.listId = listId;
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
