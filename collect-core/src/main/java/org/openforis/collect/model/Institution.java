package org.openforis.collect.model;

import org.apache.commons.lang3.StringUtils;
import org.openforis.collect.persistence.jooq.tables.pojos.OfcInstitution;

public class Institution extends OfcInstitution {

	private static final long serialVersionUID = 1L;

	public enum Visibility {
		PUBLIC('P'), PRIVATE('N');
		
		private char code;
		
		Visibility(char code) {
			this.code = code;
		}
		
		public static Visibility fromCode(char code) {
			for (Visibility value : values()) {
				if (value.code == code) {
					return value;
				}
			}
			throw new IllegalArgumentException("Invalid Institution Visibility code: " + code);
		}
		
		public char getCode() {
			return code;
		}
	}
	
	public enum InstitutionJoinRequestStatus {
		ACCEPTED('A'), REJECTED('R'), PENDING('P');
		
		private char code;
		
		InstitutionJoinRequestStatus(char code) {
			this.code = code;
		}
		
		public static InstitutionJoinRequestStatus fromCode(char code) {
			for (InstitutionJoinRequestStatus value : values()) {
				if (value.code == code) {
					return value;
				}
			}
			throw new IllegalArgumentException("Invalid InstitutionJoinRequestStatus Visibility code: " + code);
		}
		
		public char getCode() {
			return code;
		}
	}
	
	public enum InstitutionRole {
		OWNER('O'), ADMINISTRATOR('A'), DATA_ANALYZER('D'), OPERATOR('U'), VIEWER('V');
		
		private char code;
		
		InstitutionRole(char code) {
			this.code = code;
		}
		
		public static InstitutionRole fromCode(char code) {
			for (InstitutionRole value : values()) {
				if (value.code == code) {
					return value;
				}
			}
			throw new IllegalArgumentException("Invalid InstitutionRole Visibility code: " + code);
		}
		
		public char getCode() {
			return code;
		}
	}
	
	private User createdByUser;

	public User getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
		if (createdByUser != null) {
			this.setCreatedBy(createdByUser.getId());
		}
	}

	public Visibility getVisibility() {
		String code = getVisibilityCode();
		return StringUtils.isBlank(code) ? null : Visibility.fromCode(code.charAt(0));
	}

	public void setVisibility(Visibility visibility) {
		setVisibilityCode(visibility == null ? null : String.valueOf(visibility.getCode()));
	}

}
