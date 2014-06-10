package org.openforis.collect.metamodel.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openforis.commons.collection.CollectionUtils;
import org.openforis.idm.metamodel.EntityDefinition;
import org.openforis.idm.metamodel.LanguageSpecificText;
import org.openforis.idm.metamodel.LanguageSpecificTextMap;

/**
 * 
 * @author S. Ricci
 *
 */
public class Table extends Component {

	private static final long serialVersionUID = 1L;

	public enum Direction {
		BY_ROWS("byRows"), 
		BY_COLUMNS("byColumns");
		
		private String value;

		private Direction(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	private int entityId;
	private List<TableHeadingComponent> headingComponents;
	private LanguageSpecificTextMap labels;
	private boolean showRowNumbers;
	private boolean countInSummaryList;
	private Direction direction;
	
	Table(FormSection parent, int id) {
		super(parent, id);
	}
	
	public Column createColumn() {
		UIFormSets uiOptions = getUIOptions();
		return createColumn(uiOptions.nextId());
	}
	
	public Column createColumn(int id) {
		return new Column(this, id);
	}
	
	public ColumnGroup createColumnGroup() {
		UIFormSets uiOptions = getUIOptions();
		return createColumnGroup(uiOptions.nextId());
	}
	
	public ColumnGroup createColumnGroup(int id) {
		return new ColumnGroup(this, id);
	}
	
	public EntityDefinition getEntity() {
		return (EntityDefinition) getNodeDefinition(entityId);
	}

	public List<TableHeadingComponent> getHeadingComponents() {
		return CollectionUtils.unmodifiableList(headingComponents);
	}
	
	public void addHeadingComponent(TableHeadingComponent component) {
		if ( headingComponents == null ) {
			headingComponents = new ArrayList<TableHeadingComponent>();
		}
		headingComponents.add(component);
		getUIOptions().attachItem(component);
	}
	
	public void removeHeadingComponent(TableHeadingComponent component) {
		headingComponents.remove(component);
		getUIOptions().detachItem(component);
	}

	public List<LanguageSpecificText> getLabels() {
		if ( labels == null ) {
			return Collections.emptyList();
		} else {
			return labels.values();
		}
	}
	
	public String getLabel(String language) {
		return labels == null ? null: labels.getText(language);
	}
	
	public void addLabel(LanguageSpecificText label) {
		if ( labels == null ) {
			labels = new LanguageSpecificTextMap();
		}
		labels.add(label);
	}

	public void setLabel(String language, String text) {
		if ( labels == null ) {
			labels = new LanguageSpecificTextMap();
		}
		labels.setText(language, text);
	}
	
	public void removeLabel(String language) {
		labels.remove(language);
	}
	
	public int getEntityId() {
		return entityId;
	}
	
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public boolean isShowRowNumbers() {
		return showRowNumbers;
	}

	public void setShowRowNumbers(boolean showRowNumbers) {
		this.showRowNumbers = showRowNumbers;
	}

	public boolean isCountInSummaryList() {
		return countInSummaryList;
	}

	public void setCountInSummaryList(boolean countInSummaryList) {
		this.countInSummaryList = countInSummaryList;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((headingComponents == null) ? 0 : headingComponents.hashCode());
		result = prime * result + (countInSummaryList ? 1231 : 1237);
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + entityId;
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result + (showRowNumbers ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (headingComponents == null) {
			if (other.headingComponents != null)
				return false;
		} else if (!headingComponents.equals(other.headingComponents))
			return false;
		if (countInSummaryList != other.countInSummaryList)
			return false;
		if (direction != other.direction)
			return false;
		if (entityId != other.entityId)
			return false;
		if (labels == null) {
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
			return false;
		if (showRowNumbers != other.showRowNumbers)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Attribute: " + getEntity().getPath();
	}


}
