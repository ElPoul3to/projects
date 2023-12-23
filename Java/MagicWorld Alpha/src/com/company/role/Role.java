package com.company.role;

import com.company.Player;

public interface Role {

    void basicalAttack(Player var1, Player var2);

    void specialAttack(Player var1, Player var2);

    void basicalSumUp(Player var1, Player var2);

    void specialSumUp(Player var1, Player var2);

}
