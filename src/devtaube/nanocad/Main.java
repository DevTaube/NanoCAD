package devtaube.nanocad;

import devtaube.nanocad.editor.EditorRenderPipeline;
import devtaube.nanocad.editor.InputPipeline;
import devtaube.nanocad.model.ModelTexture;
import devtaube.nanocad.model.render.ModelRenderPipeline;
import rosequartz.Project;
import rosequartz.ecb.ECB;
import rosequartz.files.FileManager;
import rosequartz.files.Resource;
import rosequartz.gfx.Texture;

public class Main extends Project {

    @Override
    public void main() {
        ModelRenderPipeline modelRenderPipeline = new ModelRenderPipeline();
        ECB.add(
                // active input
                new InputHandlerPipeline(),
                // model rendering
                modelRenderPipeline,
                // editor input and rendering
                new InputPipeline(modelRenderPipeline.camera),
                new EditorRenderPipeline()
        );
        // load texture
        ECB.add(
                new ModelTexture(new Texture(new Resource("/texture.png")))
        );
        // this creates the /files/ directory, but makes it empty
        FileManager.get().writeFileString("NANOCAD-TEMPFILE.txt", "");
        FileManager.get().deleteFile("NANOCAD-TEMPFILE.txt");
    }

}
