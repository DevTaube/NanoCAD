package devtaube.nanocad.editor.cmd;

import devtaube.nanocad.model.MeshComponent;
import rosequartz.ecb.ECB;
import rosequartz.ecb.Entity;

import java.util.function.BiConsumer;

public abstract class Command {

    public abstract String[] getNames();

    public abstract int[] getValidArgCounts();

    public abstract String getArgUsage();

    public abstract String getDescription();

    private String[] arguments;

    public String run(String[] args) {
        arguments = args;
        // check if argument count valid
        boolean argumentCountValid = false;
        for(int argCount: getValidArgCounts()) {
            if(args.length - 1 == argCount) {
                argumentCountValid = true;
                break;
            }
        }
        // return error if invalid
        if(!argumentCountValid) {
            StringBuilder output = new StringBuilder("Invalid usage. Usage: ");
            for(String name: getNames()) {
                output.append("\"")
                        .append(name)
                        .append("\"")
                        .append("/");
            }
            output.deleteCharAt(output.length() - 1);
            output.append(" ");
            output.append(getArgUsage());
            throw new CommandException(output.toString());
        }
        // run
        return run();
    }

    protected int getArgCount() { return arguments.length - 1; }

    protected String getArgString(int index) {
        return arguments[index + 1];
    }

    protected int getArgInt(int index) {
        try {
            return Integer.parseInt(getArgString(index));
        } catch(NumberFormatException e) {
            throw CommandException.parseException();
        }
    }

    protected int getArgIntOptional(int index, int optionalValue) {
        if(getArgString(index).equals("_")) return optionalValue;
        return getArgInt(index);
    }

    protected float getArgFloat(int index) {
        try {
            return Float.parseFloat(getArgString(index));
        } catch(NumberFormatException e) {
            throw new CommandException("Unable to parse input.");
        }
    }

    protected float getArgFloatOptional(int index, float optionalValue) {
        if(getArgString(index).equals("_")) return optionalValue;
        return getArgFloat(index);
    }

    protected int getArgMeshIndex(int index) {
        try {
            return Integer.parseInt(getArgString(index));
        } catch(NumberFormatException ignored) {}
        int[] currentMeshIndex = { 0 };
        int[] foundMeshIndex = { -1 };
        ECB.get(MeshComponent.class, (mesh, meshComponent) -> {
            if(meshComponent.alias != null && meshComponent.alias.equals(getArgString(index)))
                foundMeshIndex[0] = currentMeshIndex[0];
            currentMeshIndex[0]++;
        });
        if(foundMeshIndex[0] == -1)
            throw new CommandException("\"" + getArgString(index) + "\" is not a valid index or an alias of any mesh.");
        return foundMeshIndex[0];
    }

    protected boolean forMesh(int index, BiConsumer<Entity, MeshComponent> action) {
        int[] currentMeshIndex = { 0 };
        boolean[] found = { false };
        ECB.get(MeshComponent.class, (mesh, meshComponent) -> {
            if(currentMeshIndex[0] == index) {
                action.accept(mesh, meshComponent);
                found[0] = true;
            }
            currentMeshIndex[0]++;
        });
        return found[0];
    }

    protected Entity getMesh(int index) {
        Entity[] mesh = { null };
        forMesh(index, (meshEntity, meshComponent) -> mesh[0] = meshEntity);
        if(mesh[0] == null) throw CommandException.meshFindException(index);
        return mesh[0];
    }

    protected void removeMesh(int index) {
        if(!forMesh(index, (meshEntity, meshComponent) -> ECB.remove(meshEntity)))
            throw CommandException.meshFindException(index);
    }

    protected MeshComponent getMeshComponent(int index) {
        MeshComponent[] mesh = { null };
        forMesh(index, (meshEntity, meshComponent) -> mesh[0] = meshComponent);
        if(mesh[0] == null) throw CommandException.meshFindException(index);
        return mesh[0];
    }

    protected abstract String run();

}
