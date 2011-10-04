
package me.escapeNT.pail.GUIComponents;

import com.google.api.translate.Language;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import me.escapeNT.pail.Pail;
import me.escapeNT.pail.Util.Localizable;
import me.escapeNT.pail.config.General;
import me.escapeNT.pail.config.PanelConfig;
import me.escapeNT.pail.config.ServerConfigHandler;
import me.escapeNT.pail.Util.UpdateHandler;
import me.escapeNT.pail.Util.Util;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Panel for editing server settings.
 * @author escapeNT
 */
public class SettingsPanel extends javax.swing.JPanel implements Localizable {

    private WaypointEditPanel waypointEditor;
    private boolean sel = true;

    /** Creates new form SettingsPanel */
    public SettingsPanel() {
        initComponents();

        // load languages
        for(Language l : Language.class.getEnumConstants()) {
            if(!l.toString().equals("")) {
                language.addItem(l.getFullName());
                if(General.getLang().toString().equals(l.toString())) {
                    language.setSelectedItem(l.getFullName());
                }
            }
        }

        waypointEditor = new WaypointEditPanel();

        craftVersion.setText(Util.translate("Craftbukkit version: ") + parseCraftVersion());
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                parseCraftUpdate();
            }
        });
        pailVersion.setText(Util.translate("Pail version: ") + Pail.PLUGIN_VERSION);
 
        autoUpdate.setSelected(General.isAutoUpdate());
        loadConfig();

        settingsTabs.add(Util.translate("Waypoints"), waypointEditor);
        //settingsTabs.add(Util.translate("Scheduler"), new SchedulerPanel());

        autoUpdate.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                General.setAutoUpdate(autoUpdate.isSelected());
                General.save();

                if(autoUpdate.isSelected() && UpdateHandler.isUpToDate() != null
                        && !UpdateHandler.isUpToDate()) {
                    new UpdateView().setVisible(true);
                }
            }
        });

        for(LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {   
            try {
                Class feel = Class.forName(laf.getClassName());
                if(((LookAndFeel)feel.newInstance()).isSupportedLookAndFeel()) {
                    themes.addItem(laf.getName());
                    if(laf.getClassName().equals(General.getLookAndFeel())) {
                        themes.setSelectedItem(laf.getName());
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(SettingsPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        translateComponent();

        tabActivationPanel.setLayout(new GridLayout(Util.getInterfaceComponents().keySet().size() / 2, 2));
    }

    private String parseCraftVersion() {
        try {
            String v = Bukkit.getServer().getVersion();
            return v.substring(v.indexOf("jnks") - 4, v.indexOf("jnks"));
        } catch(Exception ex) {
            return "0000";
        }
    }

    private void parseCraftUpdate() {
        try {
            URL url = new URL("http://ci.bukkit.org/job/dev-CraftBukkit/Recommended/buildNumber");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String v = in.readLine();
            in.close();

            boolean upToDate = false;
            if(Integer.parseInt(parseCraftVersion()) >= Integer.parseInt(v)) {
                upToDate = true;
            }

            if(upToDate) {
                update.setText(Util.translate("Latest recommended build: ") + v);
                update.setForeground(new Color(13, 190, 17));
            }
            else {
                update.setText(Util.translate("Latest recommended build: ") + v + Util.translate(" - Update required!"));
                update.setForeground(Color.red);
            }
        } catch (Exception ex) {
            update.setText(Util.translate("Latest recommended build: Unknown"));
                update.setForeground(new Color(255, 200, 33));
        }
    }

    /**
     * Loads the stored values from the server configuration.
     */
    private void loadConfig() {
        // Temporary fix until getMotd and getDifficulty are implemented
        String diff = "Easy";
        String smotd = "A minecraft server";
        try {
            BufferedReader in = new BufferedReader(new FileReader("server.properties"));
            String str;
            while((str = in.readLine()) != null) {
                if(str.indexOf("difficulty") != -1) {
                    int d = Integer.parseInt(str.split("=")[1].trim());
                    switch(d) {
                        case 0: diff = "Peaceful"; break;
                        case 1: diff = "Easy"; break;
                        case 2: diff = "Normal"; break;
                        case 3: diff = "Hard"; break;
                    }
                } else if(str.indexOf("motd") != -1) {
                    smotd = str.split("=")[1].trim();
                }
            }
            in.close();
        } catch (Exception e) {}
        // End temp fix

        Server s = Bukkit.getServer();
        World main = s.getWorlds().get(0);

        worldName.setText(main.getName());
        seed.setText(new Long(main.getSeed()).toString());
        ip.setText(s.getIp());

        nether.setSelected(s.getAllowNether());
        spawnMonsters.setSelected(main.getAllowMonsters());
        spawnAnimals.setSelected(main.getAllowAnimals());
        flight.setSelected(s.getAllowFlight());
        pvp.setSelected(main.getPVP());
        online.setSelected(s.getOnlineMode());
        whitelist.setSelected(s.hasWhitelist());

        viewDistance.setValue(s.getViewDistance());
        port.setValue(s.getPort());
        maxPlayers.setValue(s.getMaxPlayers());

        motd.setText(smotd);
        difficulty.setSelectedItem(diff);

        boolean c = s.getDefaultGameMode() == GameMode.CREATIVE;
        creative.setSelected(c);
        survival.setSelected(!c);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gameMode = new javax.swing.ButtonGroup();
        settingsTabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        worldName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ip = new javax.swing.JTextField();
        nether = new javax.swing.JCheckBox();
        spawnMonsters = new javax.swing.JCheckBox();
        spawnAnimals = new javax.swing.JCheckBox();
        online = new javax.swing.JCheckBox();
        pvp = new javax.swing.JCheckBox();
        whitelist = new javax.swing.JCheckBox();
        flight = new javax.swing.JCheckBox();
        viewDistance = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        port = new javax.swing.JSpinner();
        revert = new javax.swing.JButton();
        save = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        maxPlayers = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        seed = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        survival = new javax.swing.JRadioButton();
        creative = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        difficulty = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        motd = new javax.swing.JTextField();
        craftVersion = new javax.swing.JLabel();
        pailVersion = new javax.swing.JLabel();
        update = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        tabActivationPanel = new me.escapeNT.pail.GUIComponents.TabActivationPanel();
        reload = new javax.swing.JButton();
        autoUpdate = new javax.swing.JCheckBox();
        themes = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        language = new javax.swing.JComboBox();
        applyLang = new javax.swing.JButton();

        settingsTabs.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        settingsTabs.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        settingsTabs.setFocusable(false);

        jPanel1.setFocusable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(Util.translate("Server Properties")));

        jLabel1.setText("World name");

        worldName.setToolTipText("The name of the default world on the server");

        jLabel2.setText("World seed");

        ip.setToolTipText("Set this if you want the server to bind to a particular IP.");

        nether.setText("Allow nether");
        nether.setToolTipText("Allow portal transport to the nether.");

        spawnMonsters.setText("Spawn monsters");
        spawnMonsters.setToolTipText("Spawn hostile monsters.");

        spawnAnimals.setText("Spawn animals");
        spawnAnimals.setToolTipText("Spawn non-hostile animals.");

        online.setText("Online mode");
        online.setToolTipText("Server checks connecting players against minecraft's account database.");

        pvp.setText("Enable PVP");
        pvp.setToolTipText("Enable player verses player damage.");

        whitelist.setText("Whitelist enabled");
        whitelist.setToolTipText("With a whitelist enabled, users not on the list will be unable to connect.");

        flight.setText("Allow flight");
        flight.setToolTipText("Will allow users to use flight/no-clip on the server.");

        viewDistance.setToolTipText("The number of chunks sent to the client. (3-15)");

        jLabel3.setText("View distance");

        jLabel4.setText("Server port");

        port.setToolTipText("Port on which the server is running.");

        revert.setText("Revert");
        revert.setFocusable(false);
        revert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revertActionPerformed(evt);
            }
        });

        save.setText("Save");
        save.setFocusable(false);
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        jLabel5.setText("Max players");
        jLabel5.setToolTipText("The maximum number of players allowed to connect.");

        jLabel6.setText("Server IP");

        seed.setToolTipText("The seed used in generating new terrain.");

        jLabel9.setText("Game mode");

        gameMode.add(survival);
        survival.setText("Survival");

        gameMode.add(creative);
        creative.setText("Creative");
        creative.setActionCommand("Creative");

        jLabel10.setText("Difficulty");

        difficulty.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Peaceful", "Easy", "Normal", "Hard" }));

        jLabel11.setText("MOTD");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(14, 14, 14)
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(worldName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 240, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(jPanel2Layout.createSequentialGroup()
                .add(13, 13, 13)
                .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(11, 11, 11)
                .add(seed, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 240, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .add(192, 192, 192)
                .add(revert)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(save)
                .add(42, 42, 42))
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(spawnMonsters, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(online, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(spawnAnimals, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(whitelist, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(flight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 310, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 110, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(viewDistance, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(15, 15, 15)
                        .add(jLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(maxPlayers, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jLabel10, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(difficulty, 0, 185, Short.MAX_VALUE))
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(port, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 96, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jLabel9)
                                .add(11, 11, 11)
                                .add(survival, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(creative, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                        .add(56, 56, 56))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(nether, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(pvp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(86, 86, 86)
                        .add(motd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 238, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(32, 32, 32))
            .add(jPanel2Layout.createSequentialGroup()
                .add(14, 14, 14)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jLabel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jLabel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(ip, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 240, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(6, 6, 6)
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(worldName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(12, 12, 12)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(6, 6, 6)
                        .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(seed, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(12, 12, 12)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ip, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jLabel11, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(motd))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 14, Short.MAX_VALUE)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(nether)
                    .add(pvp))
                .add(17, 17, 17)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(spawnMonsters)
                    .add(online))
                .add(17, 17, 17)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(spawnAnimals)
                    .add(whitelist))
                .add(17, 17, 17)
                .add(flight)
                .add(7, 7, 7)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(viewDistance, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(maxPlayers, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(10, 10, 10)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(port, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel9)
                    .add(creative)
                    .add(survival))
                .add(18, 18, 18)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel10)
                    .add(difficulty, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(save)
                    .add(revert))
                .addContainerGap())
        );

        craftVersion.setText("Craftbukkit version:");

        pailVersion.setText("Pail version:");
        pailVersion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pailVersionMouseClicked(evt);
            }
        });

        update.setText("Latest recommended build:");

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(Util.translate("Active Tabs")));

        org.jdesktop.layout.GroupLayout tabActivationPanelLayout = new org.jdesktop.layout.GroupLayout(tabActivationPanel);
        tabActivationPanel.setLayout(tabActivationPanelLayout);
        tabActivationPanelLayout.setHorizontalGroup(
            tabActivationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        tabActivationPanelLayout.setVerticalGroup(
            tabActivationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 270, Short.MAX_VALUE)
        );

        tabActivationPanel.setBounds(10, 20, 400, 270);
        jLayeredPane1.add(tabActivationPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        reload.setText("Save");
        reload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadActionPerformed(evt);
            }
        });
        reload.setBounds(320, 290, 80, 30);
        jLayeredPane1.add(reload, javax.swing.JLayeredPane.DEFAULT_LAYER);

        autoUpdate.setText("Automatically check for updates");
        autoUpdate.setFocusable(false);

        themes.setFocusable(false);
        themes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                themesItemStateChanged(evt);
            }
        });

        jLabel7.setText("Skin");

        jLabel8.setText("Language");

        language.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                languageItemStateChanged(evt);
            }
        });

        applyLang.setText("Apply language");
        applyLang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyLangActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 375, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLayeredPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(craftVersion, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                        .add(10, 10, 10))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(update, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                        .add(10, 10, 10))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(160, 160, 160)
                        .add(autoUpdate, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(pailVersion, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                        .add(290, 290, 290))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, applyLang, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jLabel8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jLabel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                                .add(45, 45, 45)))
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(themes, 0, 336, Short.MAX_VALUE)
                            .add(language, 0, 336, Short.MAX_VALUE))
                        .add(11, 11, 11)))
                .add(61, 61, 61))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
                        .add(craftVersion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(update, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(autoUpdate)
                            .add(pailVersion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(17, 17, 17)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(themes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(10, 10, 10)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(language, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(10, 10, 10)
                        .add(applyLang, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLayeredPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        settingsTabs.addTab("General", jPanel1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(settingsTabs, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 946, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(settingsTabs, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void reloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadActionPerformed
        for(JCheckBox b : tabActivationPanel.getBoxes().values()) {
            PanelConfig.getPanelsActivated().put(b.getText(), b.isSelected());
        }
        PanelConfig.save();
        Util.getPlugin().getMainWindow().loadPanels();
}//GEN-LAST:event_reloadActionPerformed

    private void pailVersionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pailVersionMouseClicked
        new AboutView().setVisible(true);
}//GEN-LAST:event_pailVersionMouseClicked

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        HashMap<String, String> saveData = new HashMap<String, String>();

        saveData.put("server-ip", ip.getText());
        saveData.put("level-name", worldName.getText());
        saveData.put("level-seed", seed.getText());
        saveData.put("motd", motd.getText());

        saveData.put("allow-nether", Boolean.toString(nether.isSelected()));
        saveData.put("spawn-monsters", Boolean.toString(spawnMonsters.isSelected()));
        saveData.put("spawn-animals", Boolean.toString(spawnAnimals.isSelected()));
        saveData.put("allow-flight", Boolean.toString(flight.isSelected()));
        saveData.put("pvp", Boolean.toString(pvp.isSelected()));
        saveData.put("online-mode", Boolean.toString(online.isSelected()));
        saveData.put("white-list", Boolean.toString(whitelist.isSelected()));

        saveData.put("view-distance", viewDistance.getValue().toString());
        saveData.put("server-port", port.getValue().toString());
        saveData.put("max-players", maxPlayers.getValue().toString());

        saveData.put("difficulty", Integer.toString(diffStringToInt(difficulty.getSelectedItem().toString())));
        saveData.put("gamemode", Integer.toString((survival.isSelected() ? 0 : 1)));

        ServerConfigHandler.save(saveData);

        JOptionPane.showMessageDialog(Util.getPlugin().getMainWindow(),
                "Server config saved!\nRestart the server to apply.", "Config Saved", JOptionPane.INFORMATION_MESSAGE);
}//GEN-LAST:event_saveActionPerformed

    private void revertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revertActionPerformed
        loadConfig();
}//GEN-LAST:event_revertActionPerformed

    private void themesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_themesItemStateChanged
       if(evt.getStateChange() == ItemEvent.SELECTED && getThemes().getSelectedItem() != null) {
            for(final LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
                if(laf.getName().equals((String)getThemes().getSelectedItem())) {
                    try {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                try {
                                    UIManager.setLookAndFeel((LookAndFeel) Class.forName(laf.getClassName()).newInstance());
                                } catch(Exception ex) {
                                    Logger.getLogger(SettingsPanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                UIManager.getLookAndFeelDefaults().put("ClassLoader", getClass().getClassLoader());
                                Util.getPlugin().getMainWindow().getRootPane().updateUI();
                                Util.getPlugin().getMainWindow().getJMenuBar().updateUI();
                                SwingUtilities.updateComponentTreeUI(Util.getPlugin().getMainWindow());
                            }
                        });
                    } catch (Exception ex) {
                        Logger.getLogger(SettingsPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_themesItemStateChanged

    private void languageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_languageItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED && !sel) {
            General.setLang(Language.fromString((String)language.getSelectedItem()));
        } else {
            sel = false;
        }
    }//GEN-LAST:event_languageItemStateChanged

    private void applyLangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyLangActionPerformed
        Util.getPlugin().saveState();
        Bukkit.getServer().dispatchCommand(Util.getConsoleSender(), "reload");
    }//GEN-LAST:event_applyLangActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applyLang;
    private javax.swing.JCheckBox autoUpdate;
    private javax.swing.JLabel craftVersion;
    private javax.swing.JRadioButton creative;
    private javax.swing.JComboBox difficulty;
    private javax.swing.JCheckBox flight;
    private javax.swing.ButtonGroup gameMode;
    private javax.swing.JTextField ip;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox language;
    private javax.swing.JSpinner maxPlayers;
    private javax.swing.JTextField motd;
    private javax.swing.JCheckBox nether;
    private javax.swing.JCheckBox online;
    private javax.swing.JLabel pailVersion;
    private javax.swing.JSpinner port;
    private javax.swing.JCheckBox pvp;
    private javax.swing.JButton reload;
    private javax.swing.JButton revert;
    private javax.swing.JButton save;
    private javax.swing.JTextField seed;
    private javax.swing.JTabbedPane settingsTabs;
    private javax.swing.JCheckBox spawnAnimals;
    private javax.swing.JCheckBox spawnMonsters;
    private javax.swing.JRadioButton survival;
    private me.escapeNT.pail.GUIComponents.TabActivationPanel tabActivationPanel;
    private javax.swing.JComboBox themes;
    private javax.swing.JLabel update;
    private javax.swing.JSpinner viewDistance;
    private javax.swing.JCheckBox whitelist;
    private javax.swing.JTextField worldName;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the waypointEditor
     */
    public WaypointEditPanel getWaypointEditor() {
        return waypointEditor;
    }

    /**
     * @return the theme
     */
    public javax.swing.JComboBox getThemes() {
        return themes;
    }

    public final void translateComponent() {
        Util.translateTextComponent(jLabel1);
        Util.translateTextComponent(jLabel2);
        Util.translateTextComponent(jLabel3);
        Util.translateTextComponent(jLabel4);
        Util.translateTextComponent(jLabel5);
        Util.translateTextComponent(jLabel6);
        Util.translateTextComponent(jLabel7);
        Util.translateTextComponent(jLabel8);
        Util.translateTextComponent(jLabel9);
        Util.translateTextComponent(jLabel10);
        Util.translateTextComponent(jLabel11);
        Util.translateTextComponent(applyLang);
        Util.translateTextComponent(autoUpdate);
        Util.translateTextComponent(flight);
        Util.translateTextComponent(nether);
        Util.translateTextComponent(online);
        Util.translateTextComponent(pvp);
        Util.translateTextComponent(reload);
        Util.translateTextComponent(revert);
        Util.translateTextComponent(save);
        Util.translateTextComponent(spawnAnimals);
        Util.translateTextComponent(spawnMonsters);
        Util.translateTextComponent(whitelist);
        Util.translateTextComponent(creative);
        Util.translateTextComponent(survival);
    }

    private int diffStringToInt(String d) {
        Integer diff = null;
        if(d.equals("Peaceful")) {
            diff = 0;
        } else if(d.equals("Easy")) {
            return 1;
        } else if(d.equals("Normal")) {
            return 2;
        } else if(d.equals("Hard")) {
            return 3;
        }
        return diff;
    }
}