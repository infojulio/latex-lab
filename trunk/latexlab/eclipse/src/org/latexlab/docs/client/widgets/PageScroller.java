package org.latexlab.docs.client.widgets;

import org.latexlab.docs.client.commands.SystemViewPageCommand;
import org.latexlab.docs.client.commands.SystemViewPageIndexCommand;
import org.latexlab.docs.client.commands.SystemViewPageZoomInCommand;
import org.latexlab.docs.client.commands.SystemViewPageZoomOutCommand;
import org.latexlab.docs.client.content.icons.Icons;
import org.latexlab.docs.client.events.CommandEvent;
import org.latexlab.docs.client.events.CommandHandler;
import org.latexlab.docs.client.events.HasCommandHandlers;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;

/**
 * A page scroller widget.
 */
public class PageScroller extends Composite implements HasCommandHandlers {

  private int currentPage = 0, totalPages = 0;
  private PushButton indexButton, prevButton, nextButton, zinButton, zoutButton;
  private HandlerManager manager;
  private HorizontalPanel panel;
  private Label stateLabel;
  
  /**
   * Constructs a PageScroller.
   */
  public PageScroller() {
	manager = new HandlerManager(this);
	panel = new HorizontalPanel();
	panel.setStylePrimaryName("lab-PageScroller");
    panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	stateLabel = new Label();
	stateLabel.setStylePrimaryName("lab-PageScroller-Caption");
	indexButton = buildButton("Page Index", Icons.editorIcons.PageIndex(), new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
		  CommandEvent.fire(PageScroller.this, new SystemViewPageIndexCommand());
		}
	});
    prevButton = buildButton("Previous Page", Icons.editorIcons.PagePrevious(), new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
		  int page = previousPage();
		  CommandEvent.fire(PageScroller.this, new SystemViewPageCommand(page));
		}
	});
	nextButton = buildButton("Next Page", Icons.editorIcons.PageNext(), new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
		  int page = nextPage();
		  CommandEvent.fire(PageScroller.this, new SystemViewPageCommand(page));
		}
	});
	zinButton = buildButton("Zoom In", Icons.editorIcons.PageZoomIn(), new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
		  CommandEvent.fire(PageScroller.this, new SystemViewPageZoomInCommand());
		}
	});
	zoutButton = buildButton("Zoom Out", Icons.editorIcons.PageZoomOut(), new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
		  CommandEvent.fire(PageScroller.this, new SystemViewPageZoomOutCommand());
		}
	});
	panel.add(indexButton);
	panel.add(prevButton);
	panel.add(stateLabel);
	panel.add(nextButton);
	panel.add(zinButton);
	panel.add(zoutButton);
	FlowPanel main = new FlowPanel();
	main.add(panel);
	initWidget(main);
	setPage(0);
  }
  
  @Override
  public HandlerRegistration addCommandHandler(CommandHandler handler) {
	return manager.addHandler(CommandEvent.getType(), handler);
  }
  
  /**
   * Builds a push button.
   * 
   * @param title the button's title
   * @param image the button's icon
   * @param handler the click handler
   * @return the new push button
   */
  private PushButton buildButton(String title, AbstractImagePrototype image, ClickHandler handler) {
	PushButton button = new PushButton(image.createImage());
	button.setTitle(title);
	button.addClickHandler(handler);
	return button;
  }
  
  @Override
  public void fireEvent(GwtEvent<?> event) {
	manager.fireEvent(event);
  }
  
  /**
   * Selects the next page.
   * 
   * @return the current page's index
   */
  public int nextPage() {
	return setPage(currentPage + 1);
  }
  
  /**
   * Selects the previous page.
   * 
   * @return the current page's index
   */
  public int previousPage() {
	return setPage(currentPage - 1);
  }

  /**
   * Resets the page view to the first page.
   * 
   * @param pageCount the total number of pages
   */
  public void reset(int pageCount) {
	totalPages = pageCount;
	setPage(0);
  }
  
  /**
   * Sets the selected page's index.
   * 
   * @param page the selected page's index
   * @return the selected page's index
   */
  public int setPage(int page) {
	if (totalPages == 0) {
	  stateLabel.setText("No Pages");
	  return 0;
	} else {
	  if (page < 0) {
		page = 0;
	  } else if (page >= totalPages) {
		page = totalPages - 1;
	  }
	  currentPage = page;
	  stateLabel.setText("Page " + (currentPage + 1) + " of " + totalPages);
	  return page;
	}
  }
  
}