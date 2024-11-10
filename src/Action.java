/**
 * An action that can be taken by an entity
 */
public final class Action {
    public ActionKind kind;
    public Entity entity;
    public WorldModel world;
    public ImageStore imageStore;
    public int repeatCount;

    public Action(ActionKind kind, Entity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        this.kind = kind;
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    public static Action createActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
        return new Action(ActionKind.ACTIVITY, entity, world, imageStore, 0);
    }

    public static Action createAnimationAction(Entity entity, int repeatCount) {
        return new Action(ActionKind.ANIMATION, entity, null, null, repeatCount);
    }

    public void executeActivityAction(EventScheduler scheduler) {
        switch (this.entity.kind) {
            case SAPLING -> this.entity.executeSaplingActivity(this.world, this.imageStore, scheduler);
            case TREE -> this.entity.executeTreeActivity(this.world, this.imageStore, scheduler);
            case FAIRY -> this.entity.executeFairyActivity(this.world, this.imageStore, scheduler);
            case DUDE_NOT_FULL -> this.entity.executeDudeNotFullActivity(this.world, this.imageStore, scheduler);
            case DUDE_FULL -> this.entity.executeDudeFullActivity(this.world, this.imageStore, scheduler);
            default ->
                    throw new UnsupportedOperationException(String.format("executeActivityAction not supported for %s", this.entity.kind));
        }
    }

    public void executeAnimationAction(EventScheduler scheduler) {
        this.entity.nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.entity, createAnimationAction(this.entity, Math.max(this.repeatCount - 1, 0)), this.entity.getAnimationPeriod());
        }
    }

    public void executeAction(EventScheduler scheduler) {
        switch (this.kind) {
            case ACTIVITY -> this.executeActivityAction(scheduler);
            case ANIMATION -> this.executeAnimationAction(scheduler);
        }
    }
}
