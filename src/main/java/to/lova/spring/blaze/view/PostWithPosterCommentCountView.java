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
package to.lova.spring.blaze.view;

import com.blazebit.persistence.SubqueryInitiator;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.MappingSubquery;
import com.blazebit.persistence.view.SubqueryProvider;

import to.lova.spring.blaze.entity.Post;

@EntityView(Post.class)
public interface PostWithPosterCommentCountView {
    @MappingSubquery(CommentCountProvider.class)
    Long getCommentCount();

    class CommentCountProvider implements SubqueryProvider {
        @Override
        public <T> T createSubquery(SubqueryInitiator<T> subqueryInitiator) {
            return subqueryInitiator.from(Post.class, "my_post_")
                    .where("my_post_.comments.id")
                    .eqExpression("embedding_view(comments.id)").select("count(*)")
                    .where("my_post_.comments.commenter")
                    .eqExpression("embedding_view(poster)").end();
        }
    }
}
