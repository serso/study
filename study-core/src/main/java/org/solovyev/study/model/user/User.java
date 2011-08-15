package org.solovyev.study.model.user;

import org.jetbrains.annotations.Nullable;
import org.solovyev.common.utils.CollectionsUtils;
import org.solovyev.study.model.data_object.DataObject;
import org.solovyev.study.model.partner.Partner;
import org.solovyev.study.model.partner.PartnerRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springmodules.validation.bean.conf.loader.annotation.handler.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: serso
 * Date: Oct 16, 2009
 * Time: 12:57:17 AM
 */

public class User extends DataObject<Integer> implements Cloneable, Serializable, UserDetails {

	@NotBlank
	@Length(max = 255)
	private String username = null;

	@NotBlank
	@Length(max = 255)
	private String password = null;

	@NotBlank
	@Email
	@Length(max = 255)
	private String email = null;

	@NotNull
	@NotEmpty
	private List<UserRole> userRoles = new ArrayList<UserRole>();

	@NotNull
	private List<Partner> linkedPartners = new ArrayList<Partner>();

	private boolean doNotChangePassword = false;

	private boolean enabled = true;

	public User() {
		super(null);
	}

	public User(Integer id) {
		super(id);
	}

	public User(Integer id, String userName, String password, String email) {
		super(id);
		this.username = userName;
		this.password = password;
		this.email = email;
	}

	public User(User user) {
		super(user.getId());
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.email = user.getEmail();
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>(userRoles);
	}

	@NotNull
	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(@Nullable List<UserRole> userRoles) {
		this.userRoles = (List<UserRole>)CollectionsUtils.setNotNull(userRoles, this.userRoles);
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@NotNull
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	@SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException"})
	public User clone() {
		return (User) super.clone();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isDoNotChangePassword() {
		return doNotChangePassword;
	}

	public void setDoNotChangePassword(boolean doNotChangePassword) {
		this.doNotChangePassword = doNotChangePassword;
	}

	@NotNull
	public List<Partner> getLinkedPartners() {
		return linkedPartners;
	}

	public void setLinkedPartners(@Nullable List<Partner> linkedPartners) {
		this.linkedPartners = (List<Partner>)CollectionsUtils.setNotNull(linkedPartners, this.linkedPartners);
	}

	public Partner getLinkedPartner(PartnerRole partnerRole) {

		for (Partner linkedPartner : linkedPartners) {
			if (linkedPartner.isActsAs(partnerRole)) {
				return linkedPartner;
			}
		}

		return null;
	}

	@NotNull
	public List<Partner> getLinkedPartners(PartnerRole partnerRole) {
		final List<Partner> result = new ArrayList<Partner>();

		for (Partner linkedPartner : linkedPartners) {
			if (linkedPartner.isActsAs(partnerRole)) {
				result.add(linkedPartner);
			}
		}

		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("User");
		sb.append("{username='").append(username).append('\'');
		sb.append(", password='").append(password).append('\'');
		sb.append(", email='").append(email).append('\'');
		sb.append(", userRoles=").append(userRoles);
		sb.append(", linkedPartners=").append(linkedPartners);
		sb.append('}');
		sb.append('\'').append("super class:").append('\'').append(super.toString());
		return sb.toString();
	}

	public boolean hasAnyRoles(UserRole... userRoles) {
		boolean result = false;
		if (userRoles != null) {
			for (UserRole userRole : userRoles) {
				if (getUserRoles().contains(userRole)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
}