package com.example.dt_test

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.dt_test.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class QuizFragment : Fragment() {
    private val questionView by lazy {
        Log.e("boba", "test1")
        view?.findViewById<TextView>(R.id.tvQuestion)
    }

    private val btnA by lazy {
        view?.findViewById<Button>(R.id.buttonA)
    }
    private val btnB by lazy {
        view?.findViewById<Button>(R.id.buttonB)
    }
    private val btnC by lazy {
        view?.findViewById<Button>(R.id.buttonC)
    }

    private var questionNumber = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionView?.let {
            Log.e("boba", "test")
            val item = quizList[questionNumber]
            it.setText( "${item.question}${item.aAns}${item.bAns}${item.cAns}")
        }

        btnA?.setOnClickListener {
            Log.e("boba", "test")
            onAnswerClick(1, quizList[questionNumber].rightNumber)
        }
        btnB?.setOnClickListener {
            onAnswerClick(2, quizList[questionNumber].rightNumber)
        }
        btnC?.setOnClickListener {
            onAnswerClick(3, quizList[questionNumber].rightNumber)
        }
    }

    private fun onAnswerClick(buttonNumber: Int, rightNumber: Int) {
        if (buttonNumber == rightNumber) {
            Toast.makeText(requireContext(), "CORRECT!", Toast.LENGTH_SHORT).show()
            nextQuestion()
        } else {
            Toast.makeText(requireContext(), "WRONG!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun nextQuestion() {
        questionNumber++
        questionView?.let {
            val item = quizList[questionNumber]
            it.setText( "${item.question}${item.aAns}${item.bAns}${item.cAns}")
        }
    }

    private val quizList = listOf<QuizContent>(
        QuizContent("Where did the first modern Summer Olympic games take place in 1896?\n\n", "A) Athens, Greece\n\n", "B) Berlin, Germany\n\n", "C) Paris, France", 1),
        QuizContent("What is the nickname for Arsenal FC?\n\n","A) The Killers\n\n", "B) The Soldiers\n\n", "C) The Gunners" , 3),
        QuizContent("What number shirt is worn by a fullback in rugby union?\n\n", "A) 11\n\n", "B) 15\n\n", "C) 13",2),
        QuizContent("Which boxer did Muhammad Ali fight in ‘The Rumble in the Jungle’?\n\n", "A) George Foreman\n\n", "B) Mike Tyson\n\n", "C) Rocky Marciano", 1),
//                "How many Premier League titles did Manchester United win under Sir Alex Ferguson?\n" +
//                "In what year was the first edition of The Hundred cricket tournament?\n" +
//                "Which Irish footballer was sent home before the start of the 2002 FIFA World Cup after a public quarrel with his manager?\n" +
//                "Ricky Hatton’s first career loss was to which other boxer?\n" +
//                "What date do all racehorses in the Northern Hemisphere celebrate their birthday?\n" +
//                "In which sport would you commonly use the terms; chukka, neckshot and millionaire’s shot?\n" +
//                "Which French city is known for its 24 hour motor race?\n" +
//                "What is the name of the only racehorse to win the Grand National 3 times?\n" +
//                "Which football club has stands named after Graham Taylor and Sir Elton John?\n" +
//                "Which sport takes place in a velodrome?\n" +
//                "In which 2 countries did the 2019 Cricket World Cup take place?\n" +
//                "Follower and rover are positions in which sport?\n" +
//                "Which nation won the 2019 Rugby World Cup?\n" +
//                "What is the term for a score of 1 over par on a golf hole?\n" +
//                "Andy Murray’s first Grand Slam title was at which tennis tournament?\n" +
//                "At which football stadium would you find The Shankly Gates?\n" +
//                "How many Olympic gold medals did Michael Phelps win?\n" +
//                "Which sport do the New York Giants play?\n" +
//                "What is the national sport of Japan?\n" +
//                "Which MLS franchise team does David Beckham own?\n" +
//                "How many horses start the Grand National at Aintree?\n" +
//                "What country is Roger Federer from?\n" +
//                "What are the indentations on a golf ball commonly called?\n" +
//                "In karate, what colour belt comes right before black?\n" +
//                "Which country won the first FIFA World Cup in 1930?\n" +
//                "How many days does Wimbledon traditionally last?\n" +
//                "Which snooker player has also written crime fiction novels called ‘Framed’, ‘Double Kiss’ and ‘The Break’?\n" +
//                "Which golfer was known as ‘The Big Easy’?\n" +
//                "What sport did Princess Anne compete in at the 1976 Summer Olympic Games?\n" +
//                "How many points are awarded for a touchdown in American Football?\n" +
//                "Who was the first Olympic gymnast to score a perfect 10?\n" +
//                "Conor McGregor held UFC titles in which 2 weight classes simultaneously?\n" +
//                "Who was the last English manager to win English football’s top division?\n" +
//                "Which Formula One team did Lewis Hamilton compete for from 2007 to 2012?\n" +
//                "In darts, what is the highest score from a single dart?\n" +
//                "Which football manager is known as ‘The Special One’?\n" +
//                "The Boat Race is an annual event between which 2 universities?\n" +
//                "In football, how many yards is a penalty kick taken from goal?\n" +
//                "The boxer Prince Nazeem was born and raised in which English city?\n" +
//                "What does NASCAR stand for?\n" +
//                "Which team won the FA Cup in 2013?\n" +
//                "In what round did Anthony Joshua defeat Wladimir Klitschko in 2017?\n" +
//                "Which 2 players scored in England’s 2-1 victory over Germany in the UEFA Women’s Euro 2022 Final?\n" +
//                "Where do Warwickshire County Cricket Club play their home games?\n" +
//                "Who performed at the Super Bowl LV half time show in 2021?\n" +
//                "Which football team’s victory in a Champions League final is known as the ‘Miracle of Istanbul’?"
    )
}