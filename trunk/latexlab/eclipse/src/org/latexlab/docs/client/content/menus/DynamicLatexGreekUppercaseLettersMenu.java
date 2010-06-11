package org.latexlab.docs.client.content.menus;

import org.latexlab.docs.client.commands.SystemPasteCommand;
import org.latexlab.docs.client.content.latex.LatexCommand;
import org.latexlab.docs.client.content.latex.LatexCommandSet;
import org.latexlab.docs.client.content.latex.SetGreekUppercase;
import org.latexlab.docs.client.events.HasCommandHandlers;
import org.latexlab.docs.client.widgets.DynamicMenuBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MenuItem;

/**
 * Contains a LaTeX Greek Uppercase Letters menu with on-demand loading.
 */
public class DynamicLatexGreekUppercaseLettersMenu extends DynamicMenuBar {

  protected static DynamicLatexGreekUppercaseLettersMenu instance;
	
  /**
   * Retrieves the single instance of this class.
   * 
   * @param commandSource the command source.
   */
  public static DynamicLatexGreekUppercaseLettersMenu get(HasCommandHandlers commandSource) {
    if (instance == null) {
      instance = new DynamicLatexGreekUppercaseLettersMenu(commandSource);
    }
    return instance;
  }
  
  /**
   * Constructs a latex greek uppercase letters menu.
   * 
   * @param commandSource the command source
   */
  protected DynamicLatexGreekUppercaseLettersMenu(HasCommandHandlers commandSource) {
    super(true, commandSource);
  }

  /**
   * Asynchronously loads the MenuBar's sub items.
   * 
   * @param callback the callback carrying the sub items
   */
  @Override
  protected void getSubMenu(final AsyncCallback<MenuItem[]> callback) {
    GWT.runAsync(new RunAsyncCallback() {
		@Override
		public void onFailure(Throwable reason) {
	      callback.onFailure(reason);
		}
		@Override
		public void onSuccess() {
		  LatexCommandSet set = SetGreekUppercase.get();
		  LatexCommand[] cmds = set.getCommands();
		  MenuItem[] items = new MenuItem[cmds.length];
		  for (int i=0; i<cmds.length; i++) {
			LatexCommand cmd = cmds[i];
			items[i] = DynamicLatexGreekUppercaseLettersMenu.this.createItem(cmd.getIcon(), cmd.getTitle(), new SystemPasteCommand(cmd.getText()));
		  }
		  callback.onSuccess(items);
		}
    });
  }

}
