package com.sparklep.preorder.domain.wishlist.entity;

import com.sparklep.preorder.global.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class WishlistProduct extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productNo", nullable = false)
    private Long productNo;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "isDeleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "wishlistId")
    private  Wishlist wishlist;

    public void updateCount(int count) {
        this.count = count;
    }
    public void setDeleted() {
        this.isDeleted = true;
    }
}
