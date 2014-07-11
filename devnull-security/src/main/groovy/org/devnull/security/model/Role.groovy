package org.devnull.security.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.validation.constraints.Pattern
import javax.persistence.*

@Entity
@Table(name = "SecurityRole", uniqueConstraints = [
@UniqueConstraint(columnNames = ["name"])
])
@EqualsAndHashCode
@ToString(includeNames = true)
class Role implements Serializable {


    @Id
    @TableGenerator(name = "ROLE_GEN",
            table = "SEQUENCES",
            pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_NUMBER",
            pkColumnValue = "ROLE_SEQ",
            allocationSize=1,
            initialValue = 10)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ROLE_GEN")
    Integer id

    @Pattern(regexp = "^ROLE_.*", message = "Role names must begin with ROLE_")
    String name

    String description

    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    @JoinTable(
    name = "SecurityUserRole",
    joinColumns = @JoinColumn(name = "RoleId"),
    inverseJoinColumns = @JoinColumn(name = "UserId")
    )
    List<User> users = []
}