package org.openforis.collect.persistence.xml.internal.unmarshal;

import static org.openforis.collect.metamodel.ui.UIConfigurationConstants.UI_NAMESPACE_URI;

import java.io.IOException;
import java.util.List;

import org.openforis.collect.metamodel.ui.FormSet;
import org.openforis.collect.metamodel.ui.UIConfiguration;
import org.openforis.collect.metamodel.ui.UIModelObject;
import org.openforis.collect.model.CollectSurvey;
import org.openforis.collect.persistence.xml.UIConfigurationBinder;
import org.openforis.idm.metamodel.NodeDefinition;
import org.openforis.idm.metamodel.Schema;
import org.openforis.idm.metamodel.xml.XmlParseException;
import org.openforis.idm.metamodel.xml.internal.unmarshal.XmlPullReader;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author S. Ricci
 */
abstract class UIModelPR extends XmlPullReader {

	private UIConfigurationBinder binder;

	UIModelObject item;
	UIModelObject parent;
	
	UIModelPR(String tagName) {
		super(UI_NAMESPACE_URI, tagName);
	}
	
	UIConfigurationBinder getUIConfigurationBinder() {
		return binder;
	}
	
	void setUIConfigurationBinder(UIConfigurationBinder binder) {
		this.binder = binder;
		List<XmlPullReader> childPullReaders = getChildPullReaders();
		if ( childPullReaders != null ) {
			for (XmlPullReader reader : childPullReaders) {
				if ( reader instanceof UIModelPR ) {
					UIModelPR uiModelPR = (UIModelPR) reader;
					if ( uiModelPR.binder == null ) {
						uiModelPR.setUIConfigurationBinder(binder);
					}
				}
			}
		}
	}

	public UIConfiguration getUIConfiguration() {
		UIConfiguration result = binder.getUiConfiguration();
		return result;
	}
	
	public FormSetsPR getRootReader() {
		UIModelPR parent = (UIModelPR) getParentReader();
		while ( parent != null ) {
			if ( parent instanceof FormSetsPR ) {
				return (FormSetsPR) parent;
			}
		}
		return null;
	}
	
	public List<FormSet> getFormBundles() {
		UIModelPR rootReader = getRootReader();
		return rootReader == null ? null: rootReader.getFormBundles();
	}
	
	@Override
	public UIModelPR getParentReader() {
		return (UIModelPR) super.getParentReader();
	}
	
	@Override
	protected void handleChildTag(XmlPullReader childTagReader)
			throws XmlPullParserException, IOException, XmlParseException {
		if ( childTagReader instanceof UIModelPR ) {
			UIModelPR childPR = (UIModelPR) childTagReader;
			UIModelObject tmpParent = childPR.parent;
			UIModelObject tmpItem = childPR.item;
			childPR.parent = this.item;
			super.handleChildTag(childTagReader);
			childPR.parent = tmpParent;
			childPR.item = tmpItem;
		} else {
			super.handleChildTag(childTagReader);
		}
	}
	

	protected NodeDefinition getNodeDefinitionById(int nodeDefnId) {
		CollectSurvey survey = getUIConfiguration().getSurvey();
		Schema schema = survey.getSchema();
		NodeDefinition nodeDefn = schema.getDefinitionById(nodeDefnId);
		return nodeDefn;
	}

}