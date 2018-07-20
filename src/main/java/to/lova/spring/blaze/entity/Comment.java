/*-
 * Copyright 2017-2018 Axians SAIV S.p.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
-*/
package to.lova.spring.blaze.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Integer id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User commenter;

    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // private Post post;

    public Integer getId() {
        return this.id;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getCommenter() {
        return this.commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

}
