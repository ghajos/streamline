/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hortonworks.iotas.topology.component.impl;

import com.hortonworks.iotas.common.Schema;
import com.hortonworks.iotas.topology.component.IotasProcessor;
import com.hortonworks.iotas.topology.component.Stream;
import com.hortonworks.iotas.topology.component.TopologyDagVisitor;
import com.hortonworks.iotas.topology.component.rule.Rule;
import com.hortonworks.iotas.topology.component.rule.action.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a design time rules processor.
 */
public class RulesProcessor extends IotasProcessor {     //TODO: Rename to RuleProcessor
    public static final String CONFIG_KEY_RULES = "rules";
    private List<Rule> rules;

    public RulesProcessor() {
    }

    public RulesProcessor(RulesProcessor other) {
        super(other);
        this.rules = new ArrayList<>(other.getRules());
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
        for (Rule rule : rules) {
            for (Action action : rule.getActions()) {
                for (String stream : action.getOutputStreams()) {
                    addOutputStream(new Stream(stream, (Schema) null));
                }
            }
        }
        getConfig().setAny(CONFIG_KEY_RULES, rules);
    }

    @Override
    public void accept(TopologyDagVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "RulesProcessor{" +
                "rules=" + rules +
                '}';
    }
}