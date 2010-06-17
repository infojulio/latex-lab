package org.latexlab.docs.client.content.menus;

import org.latexlab.docs.client.commands.SystemPasteCommand;
import org.latexlab.docs.client.commands.SystemShowDialogCommand;
import org.latexlab.docs.client.content.dialogs.DynamicInsertHeaderDialog;
import org.latexlab.docs.client.content.dialogs.DynamicInsertImageDialog;
import org.latexlab.docs.client.content.dialogs.DynamicInsertTableDialog;
import org.latexlab.docs.client.content.icons.Icons;
import org.latexlab.docs.client.events.HasCommandHandlers;
import org.latexlab.docs.client.widgets.DynamicMenuBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Contains an Insert menu with on-demand loading.
 */
public class DynamicInsertMenu extends DynamicMenuBar {

  protected static DynamicInsertMenu instance;
	
  /**
   * Retrieves the single instance of this class.
   * 
   * @param commandSource the command source.
   */
  public static DynamicInsertMenu get(HasCommandHandlers commandSource) {
    if (instance == null) {
      instance = new DynamicInsertMenu(commandSource);
    }
    return instance;
  }
  
  /**
   * Constructs an insert menu.
   * 
   * @param commandSource the command source
   */
  protected DynamicInsertMenu(HasCommandHandlers commandSource) {
    super(true, commandSource);
  }

  /**
   * Asynchronously loads the MenuBar's sub items.
   * 
   * @param callback the callback carrying the sub items
   */
  @Override
  protected void getSubMenu(final AsyncCallback<ExtendedMenuItem[]> callback) {
    GWT.runAsync(new RunAsyncCallback() {
		@Override
		public void onFailure(Throwable reason) {
	      callback.onFailure(reason);
		}
		@Override
		public void onSuccess() {
		  callback.onSuccess(new ExtendedMenuItem[] {
			new ExtendedMenuItem(Icons.editorIcons.Blank(), "Header...", new SystemShowDialogCommand(DynamicInsertHeaderDialog.class)),
			new ExtendedMenuItem(Icons.editorIcons.Blank(), "Footnote", new SystemPasteCommand("\\footnote{}")),
			new ExtendedMenuItem(Icons.editorIcons.Blank(), "Tabular...", new SystemShowDialogCommand(DynamicInsertTableDialog.class)),
			new ExtendedMenuItem(Icons.editorIcons.Blank(), "Picture...", new SystemShowDialogCommand(DynamicInsertImageDialog.class)),
			null,
			new ExtendedMenuItem(Icons.editorIcons.Blank(), "Enumerations", DynamicEnumerationMenu.get(commandSource)),
			new ExtendedMenuItem(Icons.editorIcons.Blank(), "Code Environments", DynamicCodeEnvironmentMenu.get(commandSource)),
			new ExtendedMenuItem(Icons.editorIcons.Blank(), "Formulas", DynamicFormulasMenu.get(commandSource)),
			new ExtendedMenuItem(Icons.editorIcons.Blank(), "Floating Environments", DynamicFloatingEnvironmentMenu.get(commandSource)),
			new ExtendedMenuItem(Icons.editorIcons.Blank(), "Document Title", DynamicDocumentTitleMenu.get(commandSource)),
			new ExtendedMenuItem(Icons.editorIcons.Blank(), "Tables", DynamicTablesMenu.get(commandSource)),
			new ExtendedMenuItem(Icons.editorIcons.Blank(), "Quotations", DynamicQuotationsMenu.get(commandSource))
		  });
		}
    });
  }

}