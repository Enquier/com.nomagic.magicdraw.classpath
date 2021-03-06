package com.nomagic.magicdraw.classpath.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;

import com.nomagic.magicdraw.classpath.MDKProjectPropertyTester;
import com.nomagic.magicdraw.classpath.MagicDrawPluginProjectNatureHelper;
import com.nomagic.magicdraw.classpath.MDKProjectPropertyTester.ProjectLocation;

/**
 * @author Nicolas.F.Rouquette@jpl.nasa.gov
 *
 * Copyright 2012 Jet Propulsion Laboratory/Caltech
 */
public class MoveToMDInstallationMagicDrawPluginsFolderHandler extends AbstractHandler implements IElementUpdater {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	public static void moveMagicDrawPluginProjects(ISelection selection) throws CoreException {
		List<IProject> mdkProjects = MDKProjectPropertyTester.findMDKProjectsInSelection(selection);
		List<IProject> mdkProjectsToMove = new ArrayList<IProject>();
		for (IProject mdkProject : mdkProjects) {
			ProjectLocation loc = MDKProjectPropertyTester.getProjectLocation(mdkProject);
			switch (loc) {
			case WORKSPACE_PLUGIN:
			case OTHER_PLUGIN:
				mdkProjectsToMove.add(mdkProject);
				break;
			default:
				break;
			}
		}
		MagicDrawPluginProjectNatureHelper.moveMDKPluginsProjects(mdkProjectsToMove);
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		try {
			moveMagicDrawPluginProjects(selection);
			return null;
		} catch (CoreException e) {
			throw new ExecutionException("MoveToMDInstallationPluginsFolderHandler", e);
		}
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void updateElement(UIElement element, @SuppressWarnings("rawtypes") Map parameters) {
		IProject project = MDKProjectPropertyTester.findProjectInViewers();
		ProjectLocation loc = MDKProjectPropertyTester.getProjectLocation(project);
		if (null == loc)
			return;
	}
}
