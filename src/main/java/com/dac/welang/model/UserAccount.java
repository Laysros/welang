package com.dac.welang.model;



import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="user_account")
public class UserAccount{
	
	public static final int MAX_LENGTH_EMAIL_ADDRESS = 100;
    public static final int MAX_LENGTH_FIRST_NAME = 25;
    public static final int MAX_LENGTH_LAST_NAME = 50;
    public static final int MAX_LENGTH_USERNAME = 15;
    public static final int MAX_LENGTH_PASSWORD = 20;
    public static final int MAX_LENGTH_PROVIDERID = 25;

    public static final int MIN_LENGTH_USERNAME = 3;
    public static final int MIN_LENGTH_PASSWORD = 6;
    public static final int MIN_LENGTH_FIRST_NAME = 2;
    public static final int MIN_LENGTH_LAST_NAME = 2;
	
	
	private Long id;

    private String username;
    private String password;

    private String gender;
	private String email;
    private String firstName;
    private String lastName;
    private String userKey;
    private boolean accountLocked = false;    
    private boolean enabled = true; 
    public Collection<Authority> authorities;
	private UserSettings userSettings;
	private Set<UserAccount> owners = new HashSet<UserAccount>();
	private Set<UserAccount> friends = new HashSet<UserAccount>();
	
	
	public UserAccount() {
	}
	
	
	public UserAccount(Long id) {
		this.id = id;
	}

	public UserAccount(String username, String password, String email,String firstName,String lastName) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}


	@Id
	@GeneratedValue
	@Column(name="USER_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message="Can't be empty")
	@Email(message="Please provide a valid email address")
	@Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    @Column(unique=true, nullable = false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@NotEmpty(message="Can't be empty")
	@Size(min = 6, max = 20)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
    public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="SETTINGS_ID")
	public UserSettings getUserSettings() {
		return userSettings;
	}
	public void setUserSettings(UserSettings userSettings) {
		this.userSettings = userSettings;
	}
	
	
	@ManyToMany(mappedBy="friends")
	public Set<UserAccount> getOwners() {
		return owners;
	}


	public void setOwners(Set<UserAccount> owners) {
		this.owners = owners;
	}


	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name= "user_friend",
				joinColumns={@JoinColumn(name="USER_ID")},
				inverseJoinColumns={@JoinColumn(name="FRIEND_ID")}
			)
	public Set<UserAccount> getFriends() {
		return friends;
	}


	public void setFriends(Set<UserAccount> friends) {
		this.friends = friends;
	}

	@Override
	public int hashCode() {
		if(email==null || password==null){
			return 0;
		}
		return email.hashCode() + password.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UserAccount){
			UserAccount user = (UserAccount) obj;
			return id==null || this.id.equals(user.getId());
		}
		return false;
	}

	@NotEmpty(message="Can't be empty")

	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	
	@NotEmpty(message="Can't be empty")
	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

    @Column(name = "user_key", length =25)
	public String getUserKey() {
		return userKey;
	}


	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}


	public boolean isAccountLocked() {
		return accountLocked;
	}


	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public void setAuthorities(Collection<Authority> authorities) {
		this.authorities = authorities;
	}

	public String getUsername() {
		return username;
	}



	public boolean isEnabled() {
		return enabled;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
	public Collection<Authority> getAuthorities() {
		return authorities;
	}

	

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", username=" + username + ", password=" + password + ", gender=" + gender
				+ ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", userKey=" + userKey
				+ ", accountLocked=" + accountLocked + ", enabled=" + enabled + ", authorities=" + authorities
				+ ", userSettings=" + userSettings + ", owners=" + owners + ", friends=" + friends + "]";
	}
	
	
}
