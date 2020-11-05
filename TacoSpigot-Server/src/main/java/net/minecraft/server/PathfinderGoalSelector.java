package net.minecraft.server;

import eu.revamp.spigot.config.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.craftbukkit.util.UnsafeList;

import java.util.Iterator;
import java.util.List;

public class PathfinderGoalSelector {
    private static final Logger a = LogManager.getLogger();

    private List<PathfinderGoalSelectorItem> b = (List<PathfinderGoalSelectorItem>)new UnsafeList();

    private List<PathfinderGoalSelectorItem> c = (List<PathfinderGoalSelectorItem>)new UnsafeList();

    private final MethodProfiler d;

    private int e;

    private int f = 3;

    public PathfinderGoalSelector(MethodProfiler methodprofiler) {
        this.d = methodprofiler;
    }

    public void a(int i, PathfinderGoal pathfindergoal) {
        if (Settings.IMP.SETTINGS.PERFORMANCE.DISABLE_MOB_ABI && !(pathfindergoal instanceof PathfinderGoalBreed))
            return;
        this.b.add(new PathfinderGoalSelectorItem(i, pathfindergoal));
    }

    public void a(PathfinderGoal pathfindergoal) {
        Iterator<PathfinderGoalSelectorItem> iterator = this.b.iterator();
        while (iterator.hasNext()) {
            PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem = iterator.next();
            PathfinderGoal pathfindergoal1 = pathfindergoalselector_pathfindergoalselectoritem.a;
            if (pathfindergoal1 == pathfindergoal) {
                if (this.c.contains(pathfindergoalselector_pathfindergoalselectoritem)) {
                    pathfindergoal1.d();
                    this.c.remove(pathfindergoalselector_pathfindergoalselectoritem);
                }
                iterator.remove();
            }
        }
    }

    public void a() {
        this.d.a("goalSetup");
        if (this.e++ % this.f == 0) {
            Iterator<PathfinderGoalSelectorItem> iterator1 = this.b.iterator();
            while (iterator1.hasNext()) {
                PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem = iterator1.next();
                boolean flag = this.c.contains(pathfindergoalselector_pathfindergoalselectoritem);
                if (flag) {
                    if (b(pathfindergoalselector_pathfindergoalselectoritem) && a(pathfindergoalselector_pathfindergoalselectoritem))
                        continue;
                    pathfindergoalselector_pathfindergoalselectoritem.a.d();
                    this.c.remove(pathfindergoalselector_pathfindergoalselectoritem);
                }
                if (b(pathfindergoalselector_pathfindergoalselectoritem) && pathfindergoalselector_pathfindergoalselectoritem.a.a()) {
                    pathfindergoalselector_pathfindergoalselectoritem.a.c();
                    this.c.add(pathfindergoalselector_pathfindergoalselectoritem);
                }
            }
        } else {
            Iterator<PathfinderGoalSelectorItem> iterator1 = this.c.iterator();
            while (iterator1.hasNext()) {
                PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem = iterator1.next();
                if (!a(pathfindergoalselector_pathfindergoalselectoritem)) {
                    pathfindergoalselector_pathfindergoalselectoritem.a.d();
                    iterator1.remove();
                }
            }
        }
        this.d.b();
        this.d.a("goalTick");
        Iterator<PathfinderGoalSelectorItem> iterator = this.c.iterator();
        while (iterator.hasNext()) {
            PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem = iterator.next();
            pathfindergoalselector_pathfindergoalselectoritem.a.e();
        }
        this.d.b();
    }

    private boolean a(PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem) {
        boolean flag = pathfindergoalselector_pathfindergoalselectoritem.a.b();
        return flag;
    }

    private boolean b(PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem) {
        Iterator<PathfinderGoalSelectorItem> iterator = this.b.iterator();
        while (iterator.hasNext()) {
            PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem1 = iterator.next();
            if (pathfindergoalselector_pathfindergoalselectoritem1 != pathfindergoalselector_pathfindergoalselectoritem) {
                if (pathfindergoalselector_pathfindergoalselectoritem.b >= pathfindergoalselector_pathfindergoalselectoritem1.b) {
                    if (!a(pathfindergoalselector_pathfindergoalselectoritem, pathfindergoalselector_pathfindergoalselectoritem1) && this.c.contains(pathfindergoalselector_pathfindergoalselectoritem1)) {
                        ((UnsafeList.Itr)iterator).valid = false;
                        return false;
                    }
                    continue;
                }
                if (!pathfindergoalselector_pathfindergoalselectoritem1.a.i() && this.c.contains(pathfindergoalselector_pathfindergoalselectoritem1)) {
                    ((UnsafeList.Itr)iterator).valid = false;
                    return false;
                }
            }
        }
        return true;
    }

    private boolean a(PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem, PathfinderGoalSelectorItem pathfindergoalselector_pathfindergoalselectoritem1) {
        return ((pathfindergoalselector_pathfindergoalselectoritem.a.j() & pathfindergoalselector_pathfindergoalselectoritem1.a.j()) == 0);
    }

    class PathfinderGoalSelectorItem {
        public PathfinderGoal a;

        public int b;

        public PathfinderGoalSelectorItem(int i, PathfinderGoal pathfindergoal) {
            this.b = i;
            this.a = pathfindergoal;
        }
    }
}