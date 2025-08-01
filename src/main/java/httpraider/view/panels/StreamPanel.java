package httpraider.view.panels;

import extension.HTTPRaiderExtension;
import httpraider.view.components.ActionButton;
import httpraider.view.menuBars.ConnectionBar;
import httpraider.view.menuBars.InspectorBar;
import burp.api.montoya.ui.editor.EditorOptions;
import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.WebSocketMessageEditor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static javax.swing.SwingUtilities.invokeLater;

public class StreamPanel extends JPanel {

    private final ConnectionBar connectionBar;
    private final InspectorBar inspectorBar;
    private final EditorToolsPanel editorToolsGadget;
    private final HttpEditorPanel<HttpRequestEditor> clientRequest;
    private final HttpEditorPanel<HttpRequestEditor> requestQueue;
    private final HttpEditorPanel<WebSocketMessageEditor> responseQueue;
    private final ActionButton testButton;

    public StreamPanel() {
        super(new BorderLayout());
        connectionBar = new ConnectionBar();
        connectionBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.LIGHT_GRAY), new EmptyBorder(2, 0, 2, 0)));
        add(connectionBar, BorderLayout.NORTH);
        inspectorBar = new InspectorBar();
        editorToolsGadget = new EditorToolsPanel();
        add(inspectorBar, BorderLayout.EAST);
        clientRequest = new HttpEditorPanel<>("Client Request", HTTPRaiderExtension.API.userInterface().createHttpRequestEditor());
        requestQueue = new HttpEditorPanel<>("Request Queue", HTTPRaiderExtension.API.userInterface().createHttpRequestEditor(EditorOptions.READ_ONLY));
        responseQueue = new HttpEditorPanel<>("Response Queue", HTTPRaiderExtension.API.userInterface().createWebSocketMessageEditor(EditorOptions.READ_ONLY));
        testButton = new ActionButton("Test");
        clientRequest.setComponent(testButton);
        setResponseHTTPsearch();
        setState(ConnectionBar.State.DISCONNECTED);
        inspectorBar.expand();
    }

    public void setTestButtonActionListener(ActionListener l){
        if (testButton.getActionListeners().length != 0) testButton.removeActionListener(testButton.getActionListeners()[0]);
        testButton.addActionListener(l);
    }

    public void setBaseView() {
        deleteMainComponent();
        testButton.setVisible(false);
        JSplitPane req = new JSplitPane(JSplitPane.VERTICAL_SPLIT, clientRequest, requestQueue);
        req.setResizeWeight(0.5);
        JSplitPane main = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, req, responseQueue);
        add(main, BorderLayout.CENTER);
        requestQueue.removeComponent();
        ActionButton button = new ActionButton("-");
        requestQueue.setComponent(button, e -> {
            if (button.getText().equals("-")){
                button.setText("+");
                req.setDividerLocation(0.95);
            }
            else{
                button.setText("-");
                req.setDividerLocation(0.5);
            }
        });
        main.setResizeWeight(0.5);
        main.setDividerLocation(0.5);
    }

    public void setProxyView(JSplitPane nestedRequests) {
        deleteMainComponent();
        requestQueue.removeComponent();
        testButton.setVisible(true);
        JSplitPane queues = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, requestQueue, responseQueue);
        queues.setResizeWeight(0.5);

        JSplitPane main = new JSplitPane(JSplitPane.VERTICAL_SPLIT, nestedRequests, queues);
        main.setResizeWeight(0.5);
        main.setDividerLocation(0.5);
        queues.setDividerLocation(0.5);
        add(main, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void setProxyView(JTabbedPane nestedRequests) {
        deleteMainComponent();
        requestQueue.removeComponent();
        testButton.setVisible(true);
        JSplitPane queues = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, requestQueue, responseQueue);
        queues.setResizeWeight(0.5);
        queues.setDividerLocation(0.5);

        JSplitPane requests = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, clientRequest, nestedRequests);
        requests.setResizeWeight(0.5);
        requests.setDividerLocation(0.5);

        JSplitPane main = new JSplitPane(JSplitPane.VERTICAL_SPLIT, requests, queues);
        main.setResizeWeight(0.5);
        main.setDividerLocation(0.5);
        add(main, BorderLayout.CENTER);
        revalidate();
        repaint();
    }


    public HttpEditorPanel getClientRequesEditor(){
        return clientRequest;
    }

    public EditorToolsPanel getEditorToolsPanel() {
        return editorToolsGadget;
    }

    public HttpEditorPanel<HttpRequestEditor> getClientRequestEditor() {
        return clientRequest;
    }

    public byte[] getClientRequest() {
        return clientRequest.getBytes();
    }

    public byte[] getRequestQueue() {
        return requestQueue.getBytes();
    }

    public byte[] getResponseQueue() {
        return responseQueue.getBytes();
    }

    public void setResponseQueue(byte[] response) {
        responseQueue.setBytes(response);
    }

    public void setResponseHTTPsearch() {
        responseQueue.setSearchExpression("HTTP/1.1");
    }

    public void setResponseQueueCaretPosition(int pos) {
        responseQueue.setCaretPosition(pos);
    }

    public byte[] getResponseQueueBytes() {
        return responseQueue.getBytes();
    }

    public void addRequestQueueBytes(byte[] request) {
        requestQueue.addBytes(request);
    }

    public void addResponseQueueBytes(byte[] response) {
        responseQueue.addBytes(response);
    }

    public void setRequestQueue(byte[] request) {
        requestQueue.setBytes(request);
    }

    public void setRequestQueue(String request) {
        requestQueue.setBytes(request.getBytes());
    }

    public void setClientRequest(byte[] request) {
        clientRequest.setBytes(request);
    }

    public void updateConnectionBar(String host, int port, boolean tls) {
        invokeLater(() -> {
            connectionBar.setState(ConnectionBar.State.DISCONNECTED);
            connectionBar.setHost(host);
            connectionBar.setPort(port);
            connectionBar.setTLS(tls);
        });
    }

    public void clearQueues() {
        requestQueue.clear();
        responseQueue.clear();
    }

    public ConnectionBar getConnectionBar() {
        return connectionBar;
    }

    public void setState(ConnectionBar.State state) {
        connectionBar.setState(state);
        if (state == ConnectionBar.State.CONNECTING) {
            clearQueues();
        }
    }

    public InspectorBar getInspectorBar() {
        return inspectorBar;
    }

    private void deleteMainComponent(){
        for (Component comp : getComponents()) {
            Object constraint = ((BorderLayout) getLayout()).getConstraints(comp);
            if (BorderLayout.CENTER.equals(constraint)) {
                remove(comp);
                revalidate();
                repaint();
                break;
            }
        }
    }

}
