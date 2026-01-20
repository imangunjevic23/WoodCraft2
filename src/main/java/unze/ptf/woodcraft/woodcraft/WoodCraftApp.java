package unze.ptf.woodcraft.woodcraft;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import unze.ptf.woodcraft.woodcraft.dao.DocumentDao;
import unze.ptf.woodcraft.woodcraft.dao.DimensionDao;
import unze.ptf.woodcraft.woodcraft.dao.EdgeDao;
import unze.ptf.woodcraft.woodcraft.dao.GuideDao;
import unze.ptf.woodcraft.woodcraft.dao.ManualShapeDao;
import unze.ptf.woodcraft.woodcraft.dao.MaterialDao;
import unze.ptf.woodcraft.woodcraft.dao.NodeDao;
import unze.ptf.woodcraft.woodcraft.dao.ShapeDao;
import unze.ptf.woodcraft.woodcraft.dao.UserDao;
import unze.ptf.woodcraft.woodcraft.db.DatabaseInitializer;
import unze.ptf.woodcraft.woodcraft.service.AuthService;
import unze.ptf.woodcraft.woodcraft.service.EstimationService;
import unze.ptf.woodcraft.woodcraft.service.GeometryService;
import unze.ptf.woodcraft.woodcraft.session.SessionManager;
import unze.ptf.woodcraft.woodcraft.ui.SceneNavigator;

public class WoodCraftApp extends Application {
    @Override
    public void start(Stage stage) {
        DatabaseInitializer.initialize();
        setStageIcon(stage);

        UserDao userDao = new UserDao();
        MaterialDao materialDao = new MaterialDao();
        DocumentDao documentDao = new DocumentDao();
        DimensionDao dimensionDao = new DimensionDao();
        NodeDao nodeDao = new NodeDao();
        EdgeDao edgeDao = new EdgeDao();
        GuideDao guideDao = new GuideDao();
        ShapeDao shapeDao = new ShapeDao();
        ManualShapeDao manualShapeDao = new ManualShapeDao();

        SessionManager sessionManager = new SessionManager();
        AuthService authService = new AuthService(userDao, sessionManager);
        GeometryService geometryService = new GeometryService();
        EstimationService estimationService = new EstimationService(materialDao, shapeDao, geometryService);

        SceneNavigator navigator = new SceneNavigator(stage, sessionManager, authService, userDao, materialDao,
                documentDao, dimensionDao, nodeDao, edgeDao, guideDao, shapeDao, manualShapeDao,
                geometryService, estimationService);
        navigator.showInitialScene();
    }

    private void setStageIcon(Stage stage) {
        var iconUrl = getClass().getResource("/images/logo.png");
        if (iconUrl != null) {
            stage.getIcons().add(new Image(iconUrl.toExternalForm()));
        }
    }
}
