package org.openforis.collect.persistence.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.openforis.collect.metamodel.ui.UIOptions;
import org.openforis.collect.metamodel.ui.UIOptionsConstants;
import org.openforis.collect.metamodel.ui.UITab;
import org.openforis.collect.metamodel.ui.UITabSet;

/**
 * 
 * @author S. Ricci
 *
 */
public class UIOptionsBinderTest {

	private static final String OLD_UIOPTIONS_TEST_XML = "test-uioptions.xml";
	private static final String NEW_UIOPTIONS_TEST_XML = "test-new-model-uioptions.xml";
	
	private static final String EN_LANG_CODE = "en";

	@Test
	public void testUnmarshallOldUIModel() throws IOException {
		String optionsBody = loadOldUIModelTestOptions();
		UIOptionsBinder binder = new UIOptionsBinder();
		UIOptions uiOptions = binder.unmarshal(UIOptionsConstants.UI_TYPE, optionsBody);
		assertNotNull(uiOptions);
		List<UITabSet> tabSets = uiOptions.getTabSets();
		assertEquals(1, tabSets.size());
		UITabSet clusterRootTabSet = tabSets.get(0);
		assertEquals("cluster", clusterRootTabSet.getName());
		List<UITab> tabs = clusterRootTabSet.getTabs();
		assertEquals(4, tabs.size());
		UITab plotTab = tabs.get(1);
		assertEquals("plot", plotTab.getName());
		String label = plotTab.getLabel(EN_LANG_CODE);
		assertEquals("Plot", label);
		assertEquals(clusterRootTabSet, plotTab.getParent());
		List<UITab> plotInnerTabs = plotTab.getTabs();
		assertEquals(6, plotInnerTabs.size());
		UITab plotDetTab = plotInnerTabs.get(0);
		assertEquals("plot_det", plotDetTab.getName());
	}
	
	@Test
	public void testUnmarshallUIModel() throws IOException {
		String optionsBody = loadNewUIModelTestOptions();
		UIOptionsBinder binder = new UIOptionsBinder();
		UIOptions uiOptions = binder.unmarshal(UIOptionsConstants.UI_TYPE, optionsBody);
		assertNotNull(uiOptions);
	}
	
	@Test
	public void roundTripMarshallingTest() throws IOException {
		String optionsBody = loadNewUIModelTestOptions();
		UIOptionsBinder binder = new UIOptionsBinder();
		UIOptions uiOptions = binder.unmarshal(UIOptionsConstants.UI_TYPE, optionsBody);
		String marshalled = binder.marshal(uiOptions);
		UIOptions reloaded = binder.unmarshal(UIOptionsConstants.UI_TYPE, marshalled);
		assertEquals(uiOptions, reloaded);
	}
	
	@Test
	public void roundTripOldModelMarshallingTest() throws IOException {
		String optionsBody = loadOldUIModelTestOptions();
		UIOptionsBinder binder = new UIOptionsBinder();
		UIOptions uiOptions = binder.unmarshal(UIOptionsConstants.UI_TYPE, optionsBody);
		new File("target/test/output").mkdirs();
		FileOutputStream fos = new FileOutputStream("target/test/output/marshalled.uioptions.xml");
		String marshalled = binder.marshal(uiOptions);
		IOUtils.write(marshalled, fos);
		fos.flush();
		fos.close();
	}

	private String loadOldUIModelTestOptions() throws IOException {
		return loadTextResourceFile(OLD_UIOPTIONS_TEST_XML);
	}
	
	private String loadNewUIModelTestOptions() throws IOException {
		return loadTextResourceFile(NEW_UIOPTIONS_TEST_XML);
	}

	protected String loadTextResourceFile(String resourceName) throws IOException {
		URL fileUrl = ClassLoader.getSystemResource(resourceName);
		InputStream is = fileUrl.openStream();
		StringWriter writer = new StringWriter();
		IOUtils.copy(is, writer, "UTF-8");
		String result = writer.toString();
		return result;
	}
	
}
