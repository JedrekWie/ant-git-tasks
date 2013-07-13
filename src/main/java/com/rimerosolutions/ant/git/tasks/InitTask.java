/*
 * Copyright 2013 Rimero Solutions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rimerosolutions.ant.git.tasks;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import com.rimerosolutions.ant.git.GitBuildException;

/**
 * Task to initialize a git repository
 *
 * <a href="https://www.kernel.org/pub/software/scm/git/docs/git-init.html">git-init Manual Page</a>
 *
 * <a href="http://download.eclipse.org/jgit/docs/jgit-2.3.1.201302201838-r/apidocs/index.html?org/eclipse/jgit/api/FetchCommand.html">Underlying wrapped JGit command</a>
 *
 * @author Yves Zoundi
 */
public class InitTask extends AbstractGitTask {

        private boolean bare = false;
        private static final String TASK_NAME = "git-init";

        @Override
        public String getName() {
                return TASK_NAME;
        }

        /**
         * Whether the repository is bare or not
         *
         * @param bare (Default false)
         */
        public void setBare(boolean bare) {
                this.bare = bare;
        }

        @Override
        public void execute() {
                try {
                        Git.init().
                                setBare(bare).
                                setDirectory(new File(getDirectory().getAbsolutePath())).
                                call().
                                commit().
                                setAuthor(lookupSettings().getIdentity()).
                                setMessage("Initial commit").
                                call();
                } catch (GitAPIException e) {
                        throw new GitBuildException("Could not delete specified tags", e);
                }
        }

}
