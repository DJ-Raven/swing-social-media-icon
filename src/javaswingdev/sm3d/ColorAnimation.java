package javaswingdev.sm3d;

import java.awt.Component;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class ColorAnimation {

    private final Animator animator;
    private final SocialMediaItem item;
    private boolean show;
    private TimingTarget targetColor1;
    private TimingTarget targetColor2;
    private TimingTarget targetColor3;
    private TimingTarget targetIconColor1;
    private TimingTarget targetIconColor2;

    public ColorAnimation(Component com, SocialMediaItem item) {
        this.item = item;
        animator = new Animator(500, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                com.repaint();
            }
        });
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
    }

    public void show() {
        if (!show) {
            startAnimation(true);
        }
    }

    public void hide() {
        if (show) {
            startAnimation(false);
        }
    }

    private void startAnimation(boolean show) {
        if (animator.isRunning()) {
            float f = animator.getTimingFraction();
            animator.stop();
            animator.setStartFraction(1f - f);
        } else {
            animator.setStartFraction(0);
        }
        this.show = show;
        animator.removeTarget(targetColor1);
        animator.removeTarget(targetColor2);
        animator.removeTarget(targetColor3);
        animator.removeTarget(targetIconColor1);
        animator.removeTarget(targetIconColor2);
        if (show) {
            targetColor1 = new PropertySetter(item, "color", item.getColor(), item.getData().getColor());
            targetColor2 = new PropertySetter(item, "colorShadow1", item.getColorShadow1(), item.getData().getColorShadow1());
            targetColor3 = new PropertySetter(item, "colorShadow2", item.getColorShadow2(), item.getData().getColorShadow2());
            targetIconColor1 = new PropertySetter(item, "iconColor1", item.getIconColor1(), item.getDefaultColor());
            targetIconColor2 = new PropertySetter(item, "iconColor2", item.getIconColor2(), item.getDefaultColorShadow2());
        } else {
            targetColor1 = new PropertySetter(item, "color", item.getColor(), item.getDefaultColor());
            targetColor2 = new PropertySetter(item, "colorShadow1", item.getColorShadow1(), item.getDefaultColorShadow1());
            targetColor3 = new PropertySetter(item, "colorShadow2", item.getColorShadow2(), item.getDefaultColorShadow2());
            targetIconColor1 = new PropertySetter(item, "iconColor1", item.getIconColor1(), item.getData().getColor());
            targetIconColor2 = new PropertySetter(item, "iconColor2", item.getIconColor2(), item.getData().getColorShadow2());
        }
        animator.addTarget(targetColor1);
        animator.addTarget(targetColor2);
        animator.addTarget(targetColor3);
        animator.addTarget(targetIconColor1);
        animator.addTarget(targetIconColor2);
        animator.start();
    }
}
