package com.ll.domain.post.genFile.entity;

import com.ll.domain.base.genFile.genFile.entity.GenFile;
import com.ll.domain.post.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
class PostGenFile : GenFile {
    enum class TypeCode {
        attachment,
        thumbnail
    }

    constructor(post: Post, typeCode: TypeCode, fileNo: Int) : super(fileNo) {
        this.post = post;
        this.typeCode = typeCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    lateinit var post: Post

    @Enumerated(EnumType.STRING)
    lateinit var typeCode: TypeCode

    @Override
    override fun getOwnerModelId(): Long {
        return post.id
    }

    override fun getTypeCodeAsStr(): String {
        return typeCode.name
    }
}
