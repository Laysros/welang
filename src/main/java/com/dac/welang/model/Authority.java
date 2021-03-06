package com.dac.welang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "authorities")
public class Authority{
	public static final int MAX_LENGTH_AUTHORITY = 30;
    public static final int MIN_LENGTH_AUTHORITY = 4;

    
    private String authority;

    @Id
	@GeneratedValue
    @Column(name = "authority_id")
    protected Long id;

    @Column(name = "is_locked")
    private boolean isLocked = false;

    public Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

   

    public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authority == null) ? 0 : authority.hashCode());
        return result;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Authority other = (Authority) obj;
        if (authority == null) {
            if (other.authority != null)
                return false;
        } else if (!authority.equals(other.authority))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return authority;
    }

}
