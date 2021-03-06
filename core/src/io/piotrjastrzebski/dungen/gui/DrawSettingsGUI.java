/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package io.piotrjastrzebski.dungen.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;
import io.piotrjastrzebski.dungen.DrawSettings;

public class DrawSettingsGUI extends VisWindow {
	DrawSettings settings;
	Restarter restarter;

	VisCheckBox drawMinSpanTree;
	VisCheckBox drawHallWayPaths;
	VisCheckBox drawHallWays;
	VisCheckBox drawBodies;
	VisCheckBox drawUnused;
	VisCheckBox drawMain;
	VisCheckBox drawExtra;
	VisCheckBox drawEdges;
	VisCheckBox drawSpawnArea;

	public DrawSettingsGUI (Restarter restarter) {
		super("Display settings");
		this.restarter = restarter;
		VisUI.getSkin().getFont("default-font").getData().markupEnabled = true;
		settings = new DrawSettings();
		VisTable c = new VisTable(true);
		c.add(new VisLabel("Hover for tooltips")).row();

		drawBodies = toggle(c, "Bodies", "Draw box2d bodies used for separation", settings.drawBodies, new Toggle() {
			@Override public void toggle (boolean value) {
				settings.drawBodies = value;
			}
		});
		c.row();
		drawUnused = toggle(c, "[#9c9c9c]Unused[]", "Draw rooms that are [#9c9c9c]unused[]", settings.drawUnused, new Toggle() {
			@Override public void toggle (boolean value) {
				settings.drawUnused = value;
			}
		});
		c.row();
		drawExtra = toggle(c, "[#cccccc]extra[]", "Draw [#cccccc]extra[] rooms, added to form paths", settings.drawExtra, new Toggle() {
			@Override public void toggle (boolean value) {
				settings.drawExtra = value;
			}
		});
		c.row();
		drawHallWays = toggle(c, "[#3366ff]Hallways[]", "Draw rooms that are part of [#3366ff]hallways[]", settings.drawHallWays, new Toggle() {
			@Override public void toggle (boolean value) {
				settings.drawHallWays = value;
			}
		});
		c.row();
		drawMain = toggle(c, "[#ff3319]Main[]", "Draw [#ff3319]main[] rooms", settings.drawMain, new Toggle() {
			@Override public void toggle (boolean value) {
				settings.drawMain = value;
			}
		});
		c.row();
		String ptt = "Draw hallway paths connecting main rooms\n" +
			"[#32cd32]from min span tree[]\n" +
			"[ORANGE]reconnected[]";
		drawHallWayPaths = toggle(c, "Hallway Paths", ptt, settings.drawHallWayPaths, new Toggle() {
			@Override public void toggle (boolean value) {
				settings.drawHallWayPaths = value;
			}
		});
		c.row();
		drawEdges = toggle(c, "Triangulation", "Draw triangulation for main rooms", settings.drawEdges, new Toggle() {
			@Override public void toggle (boolean value) {
				settings.drawEdges = value;
			}
		});
		c.row();
		String msttt = "Draw minimum spanning tree for main rooms\n" +
			"[#32cd32]min span tree[]\n" +
			"[ORANGE]reconnected[]";
		drawMinSpanTree = toggle(c, "Min Span Tree", msttt, settings.drawMinSpanTree, new Toggle() {
			@Override public void toggle (boolean value) {
				settings.drawMinSpanTree = value;
			}
		});
		c.row();
		drawSpawnArea = toggle(c, "Spawn Area", "Draw initial spawn area of rooms", settings.drawSpawnArea, new Toggle() {
			@Override public void toggle (boolean value) {
				settings.drawSpawnArea = value;
			}
		});

		add(c);
		pack();
	}

	private VisCheckBox toggle (VisTable c, String text, String tt, boolean def, final Toggle toggle) {
		final VisCheckBox cb = new VisCheckBox(text, def);
		new Tooltip(cb, tt);
		cb.addListener(new ChangeListener() {
			@Override public void changed (ChangeEvent event, Actor actor) {
				toggle.toggle(cb.isChecked());
				restarter.update(settings);
			}
		});
		c.add(cb).left();
		return cb;
	}

	private abstract class Toggle {
		public abstract void toggle(boolean checked);
	}

	public void setDefaults(DrawSettings settings) {
		this.settings.copy(settings);
		drawBodies.setChecked(settings.drawBodies);
		drawUnused.setChecked(settings.drawUnused);
		drawExtra.setChecked(settings.drawExtra);
		drawHallWays.setChecked(settings.drawHallWays);
		drawHallWayPaths.setChecked(settings.drawHallWayPaths);
		drawMain.setChecked(settings.drawMain);
		drawEdges.setChecked(settings.drawEdges);
		drawMinSpanTree.setChecked(settings.drawMinSpanTree);
		drawSpawnArea.setChecked(settings.drawSpawnArea);
		pack();
	}

	public DrawSettings getSettings () {
		return settings;
	}
}
