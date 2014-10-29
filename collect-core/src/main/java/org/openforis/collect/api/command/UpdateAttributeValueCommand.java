/**
 * 
 */
package org.openforis.collect.api.command;

import org.openforis.collect.api.User;
import org.openforis.collect.api.Value;

/**
 * @author D. Wiell
 * @author S. Ricci
 *
 */
public class UpdateAttributeValueCommand extends Command {

	public final Value value;
	public final int attributeId;

	public UpdateAttributeValueCommand(int surveyId, int recordId, User user, int attributeId, Value value) {
		super(surveyId, recordId, user);
		this.attributeId = attributeId;
		this.value = value;
	}

}
