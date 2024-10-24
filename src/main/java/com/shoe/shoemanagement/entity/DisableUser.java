package com.shoe.shoemanagement.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "disableuser")
public class DisableUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "left_checkbox")
    private boolean leftCheckbox;

    @Column(name = "right_checkbox")
    private boolean rightCheckbox;

    // Additional fields
    @Column(name = "left_foot_length")
    private String leftFootLength;

    @Column(name = "right_foot_length")
    private String rightFootLength;

    @Column(name = "left_foot_width")
    private String left_foot_width;

    @Column(name = "right_foot_width")
    private String right_foot_width;

    @Column(name = "left_arch_height")
    private String left_arch_height;

    @Column(name = "right_arch_height")
    private String right_arch_height;

    @Column(name = "left_toe_shape")
    private String left_toe_shape;

    @Column(name = "right_toe_shape")
    private String right_toe_shape;

    @Column(name = "comfort_and_closure_preferences")
    private String comfortAndClosurePreferences;

    @Column(name = "description")
    private String description;

    //@ManyToOne
    //@JoinColumn(name = "user_id")
    //private User user;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isLeftCheckbox() {
        return leftCheckbox;
    }

    public void setLeftCheckbox(boolean leftCheckbox) {
        this.leftCheckbox = leftCheckbox;
    }

    public boolean isRightCheckbox() {
        return rightCheckbox;
    }

    public void setRightCheckbox(boolean rightCheckbox) {
        this.rightCheckbox = rightCheckbox;
    }

    public String getLeftFootLength() {
        return leftFootLength;
    }

    public void setLeftFootLength(String leftFootLength) {
        this.leftFootLength = leftFootLength;
    }

    public String getRightFootLength() {
        return rightFootLength;
    }

    public void setRightFootLength(String rightFootLength) {
        this.rightFootLength = rightFootLength;
    }

    public String getLeft_foot_width() {
        return left_foot_width;
    }

    public void setLeft_foot_width(String left_foot_width) {
        this.left_foot_width = left_foot_width;
    }

    public String getRight_foot_width() {
        return right_foot_width;
    }

    public void setRight_foot_width(String right_foot_width) {
        this.right_foot_width = right_foot_width;
    }

    public String getLeft_arch_height() {
        return left_arch_height;
    }

    public void setLeft_arch_height(String left_arch_height) {
        this.left_arch_height = left_arch_height;
    }

    public String getRight_arch_height() {
        return right_arch_height;
    }

    public void setRight_arch_height(String right_arch_height) {
        this.right_arch_height = right_arch_height;
    }

    public String getLeft_toe_shape() {
        return left_toe_shape;
    }

    public void setLeft_toe_shape(String left_toe_shape) {
        this.left_toe_shape = left_toe_shape;
    }

    public String getRight_toe_shape() {
        return right_toe_shape;
    }

    public void setRight_toe_shape(String right_toe_shape) {
        this.right_toe_shape = right_toe_shape;
    }

    public String getComfortAndClosurePreferences() {
        return comfortAndClosurePreferences;
    }

    public void setComfortAndClosurePreferences(String comfortAndClosurePreferences) {
        this.comfortAndClosurePreferences = comfortAndClosurePreferences;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // public User getUser() {
    //   return user;
    //}

    //public void setUser(User user) {
    //    this.user = user;
    //}
}
