package org.openforis.collect.persistence.xml.internal.marshal;

import static org.openforis.collect.metamodel.ui.UIOptionsConstants.ATTRIBUTE_ID;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.AUTOCOMPLETE;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.COLUMN;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.COUNT;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.COLUMN_GROUP;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.ENTITY_ID;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.FIELD;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.FORM;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.FORM_SET;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.FORM_BUNDLES;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.FORM_SECTION;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.ID;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.LABEL;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.NAME;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.SHOW_ROW_NUMBERS;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.TAB;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.TABLE;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.TAB_SET;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.UI_NAMESPACE_URI;
import static org.openforis.collect.metamodel.ui.UIOptionsConstants.UI_PREFIX;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.openforis.collect.metamodel.ui.Column;
import org.openforis.collect.metamodel.ui.ColumnGroup;
import org.openforis.collect.metamodel.ui.Component;
import org.openforis.collect.metamodel.ui.Field;
import org.openforis.collect.metamodel.ui.Form;
import org.openforis.collect.metamodel.ui.FormSet;
import org.openforis.collect.metamodel.ui.FormSection;
import org.openforis.collect.metamodel.ui.Table;
import org.openforis.collect.metamodel.ui.UIOptions;
import org.openforis.collect.metamodel.ui.UITab;
import org.openforis.collect.metamodel.ui.UITabSet;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.xml.IdmlConstants;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/**
 * 
 * @author S. Ricci
 *
 */
public class UIOptionsSerializer {

	private static final String INDENT = "http://xmlpull.org/v1/doc/features.html#indent-output";

	public void write(UIOptions options, Writer out) {
		try {
			XmlSerializer serializer = createXmlSerializer();
			serializer.setOutput(out);
//			List<UITabSet> tabSets = options.getTabSets();
//			for (UITabSet tabSet : tabSets) {
//				writeTabSet(serializer, tabSet);
//			}
			serializer.startTag(UI_NAMESPACE_URI, FORM_BUNDLES);
			List<FormSet> formBundles = options.getFormSets();
			for (FormSet formBundle : formBundles) {
				write(serializer, formBundle);
			}
			serializer.endTag(UI_NAMESPACE_URI, FORM_BUNDLES);
			serializer.flush();
		} catch ( Exception e ) {
			throw new RuntimeException(e);
		}
	}

	protected XmlSerializer createXmlSerializer() throws XmlPullParserException,
			IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlSerializer serializer = factory.newSerializer();
		serializer.setFeature(INDENT, true);
		serializer.setPrefix(UI_PREFIX, UI_NAMESPACE_URI);
		return serializer;
	}

	protected void write(XmlSerializer serializer, FormSet formBundle)
			throws IOException {
		serializer.startTag(UI_NAMESPACE_URI, FORM_SET);
		serializer.attribute(UI_NAMESPACE_URI, ID, Integer.toString(formBundle.getId()));
		serializer.attribute(UI_NAMESPACE_URI, ENTITY_ID, Integer.toString(formBundle.getEntityId()));
		List<LanguageSpecificText> labels = formBundle.getLabels();
		for (LanguageSpecificText label : labels) {
			writeLabel(serializer, label);
		}
		List<Form> forms = formBundle.getForms();
		for (Form form : forms) {
			write(serializer, form);
		}
		serializer.endTag(UI_NAMESPACE_URI, FORM_SET);
	}
	
	protected void write(XmlSerializer serializer, Form form)
			throws IOException {
		serializer.startTag(UI_NAMESPACE_URI, FORM);
		serializer.attribute(UI_NAMESPACE_URI, ID, Integer.toString(form.getId()));
		serializer.attribute(UI_NAMESPACE_URI, ENTITY_ID, Integer.toString(form.getEntityId()));
		List<LanguageSpecificText> labels = form.getLabels();
		for (LanguageSpecificText label : labels) {
			writeLabel(serializer, label);
		}
		List<Form> forms = form.getForms();
		for (Form subForm : forms) {
			write(serializer, subForm);
		}
		List<FormSection> formSections = form.getFormSections();
		for (FormSection formSection : formSections) {
			write(serializer, formSection);
		}
		serializer.endTag(UI_NAMESPACE_URI, FORM);
	}
	
	protected void write(XmlSerializer serializer, FormSection formSection)
			throws IOException {
		serializer.startTag(UI_NAMESPACE_URI, FORM_SECTION);
		serializer.attribute(UI_NAMESPACE_URI, ID, Integer.toString(formSection.getId()));
		List<LanguageSpecificText> labels = formSection.getLabels();
		for (LanguageSpecificText label : labels) {
			writeLabel(serializer, label);
		}
		List<Component> components = formSection.getComponents();
		for (Component component : components) {
			if ( component instanceof Field ) {
				write(serializer, (Field) component);
			} else {
				write(serializer, (Table) component);
			}
		}
		serializer.endTag(UI_NAMESPACE_URI, FORM_SECTION);
	}
	
	protected void write(XmlSerializer serializer, Field field) throws IOException {
		serializer.startTag(UI_NAMESPACE_URI, FIELD);
		serializer.attribute(UI_NAMESPACE_URI, ID, Integer.toString(field.getId()));
		serializer.attribute(UI_NAMESPACE_URI, ATTRIBUTE_ID, Integer.toString(field.getAttributeId()));
		serializer.attribute(UI_NAMESPACE_URI, AUTOCOMPLETE, field.isAutocomplete() ? Boolean.TRUE.toString(): null);
		serializer.endTag(UI_NAMESPACE_URI, FIELD);
	}

	protected void write(XmlSerializer serializer, Table table)
			throws IOException {
		serializer.startTag(UI_NAMESPACE_URI, TABLE);
		serializer.attribute(UI_NAMESPACE_URI, ID, Integer.toString(table.getId()));
		serializer.attribute(UI_NAMESPACE_URI, ENTITY_ID, Integer.toString(table.getEntityId()));
		serializer.attribute(UI_NAMESPACE_URI, COUNT, table.isCountInSummaryList() ? Boolean.TRUE.toString(): null);
		serializer.attribute(UI_NAMESPACE_URI, SHOW_ROW_NUMBERS, table.isShowRowNumbers() ? Boolean.TRUE.toString(): null);
		List<LanguageSpecificText> labels = table.getLabels();
		for (LanguageSpecificText label : labels) {
			writeLabel(serializer, label);
		}
		List<Column> columns = table.getColumns();
		for (Column column : columns) {
			write(serializer, column);
		}
		List<ColumnGroup> columnGroups = table.getColumnGroups();
		for (ColumnGroup columnGroup : columnGroups) {
			write(serializer, columnGroup);
		}
		serializer.endTag(UI_NAMESPACE_URI, TABLE);
	}
	
	protected void write(XmlSerializer serializer, ColumnGroup columnGroup)
			throws IOException {
		serializer.startTag(UI_NAMESPACE_URI, COLUMN_GROUP);
		serializer.attribute(UI_NAMESPACE_URI, ID, Integer.toString(columnGroup.getId()));
		List<LanguageSpecificText> labels = columnGroup.getLabels();
		for (LanguageSpecificText label : labels) {
			writeLabel(serializer, label);
		}
		List<Column> columns = columnGroup.getColumns();
		for (Column column : columns) {
			write(serializer, column);
		}
		serializer.endTag(UI_NAMESPACE_URI, COLUMN_GROUP);
	}
	
	protected void write(XmlSerializer serializer, Column column) throws IOException {
		serializer.startTag(UI_NAMESPACE_URI, COLUMN);
		serializer.attribute(UI_NAMESPACE_URI, ID, Integer.toString(column.getId()));
		serializer.attribute(UI_NAMESPACE_URI, ATTRIBUTE_ID, Integer.toString(column.getAttributeId()));
		serializer.endTag(UI_NAMESPACE_URI, COLUMN);
	}
	
	protected void writeTabSet(XmlSerializer serializer, UITabSet tabSet)
			throws IOException {
		serializer.startTag(UI_NAMESPACE_URI, TAB_SET);
		serializer.attribute(UI_NAMESPACE_URI, NAME	, tabSet.getName());
		List<UITab> tabs = tabSet.getTabs();
		for (UITab tab : tabs) {
			writeTab(serializer, tab);
		}
		serializer.endTag(UI_NAMESPACE_URI, TAB_SET);
	}

	protected void writeTab(XmlSerializer serializer, UITab tab) throws IOException {
		serializer.startTag(UI_NAMESPACE_URI, TAB);
		serializer.attribute(UI_NAMESPACE_URI, NAME	, tab.getName());
		List<LanguageSpecificText> labels = tab.getLabels();
		for (LanguageSpecificText label : labels) {
			writeLabel(serializer, label);
		}
		List<UITab> tabs = tab.getTabs();
		for (UITab innerTab : tabs) {
			writeTab(serializer, innerTab);
		}
		serializer.endTag(UI_NAMESPACE_URI, TAB);
	}

	protected void writeLabel(XmlSerializer serializer, LanguageSpecificText label)
			throws IOException {
		serializer.startTag(UI_NAMESPACE_URI, LABEL);
		String lang = label.getLanguage();
		if ( lang != null ) {
			serializer.attribute(IdmlConstants.XML_NAMESPACE_URI, IdmlConstants.XML_LANG_ATTRIBUTE, lang);
		}
		serializer.text(label.getText());
		serializer.endTag(UI_NAMESPACE_URI, LABEL);
	}
}
