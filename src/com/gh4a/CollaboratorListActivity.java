/*
 * Copyright 2011 Azwan Adli Abdullah
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
 */
package com.gh4a;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CollaboratorService;

import android.content.Intent;

import com.actionbarsherlock.view.MenuItem;

public class CollaboratorListActivity extends UserListActivity {

    protected String mUserLogin;
    protected String mRepoName;

    @Override
    protected void setRequestData() {
        mUserLogin = getIntent().getExtras().getString(Constants.Repository.REPO_OWNER);
        mRepoName = getIntent().getExtras().getString(Constants.Repository.REPO_NAME);
    }

    @Override
    protected int getRowLayout() {
        return R.layout.row_gravatar_1;
    }
    
    @Override
    protected String getTitleBar() {
        return getResources().getString(R.string.repo_collaborators);
    }
    
    @Override
    protected String getSubTitle() {
        return mUserLogin + "/" + mRepoName;
    }
    
    @Override
    protected boolean getShowExtraData() {
        return false;
    }

    protected List<User> getUsers() throws IOException {
        GitHubClient client = new GitHubClient();
        client.setOAuth2Token(getAuthToken());
        CollaboratorService collaboratorService = new CollaboratorService(client);
        return collaboratorService
                .getCollaborators(new RepositoryId(mUserLogin, mRepoName));
    }
    
    @Override
    public boolean setMenuOptionItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getApplicationContext().openRepositoryInfoActivity(this, mUserLogin, mRepoName, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                return true;     
            default:
                return true;
        }
    }
}
